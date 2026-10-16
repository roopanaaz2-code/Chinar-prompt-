package com.example.data

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

enum class AppThemeMode {
    DARK_NAVY,
    LIGHT_PARCHMENT,
    AMOLED_MIDNIGHT
}

class PreferencesManager(private val context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("chinar_prompt_book_prefs", Context.MODE_PRIVATE)

    var lastReadPage: Int
        get() = prefs.getInt("last_read_page", 1)
        set(value) = prefs.edit().putInt("last_read_page", value).apply()

    var fontScale: Float
        get() = prefs.getFloat("font_scale", 1.0f)
        set(value) = prefs.edit().putFloat("font_scale", value).apply()

    var reducedMotion: Boolean
        get() = prefs.getBoolean("reduced_motion", false)
        set(value) = prefs.edit().putBoolean("reduced_motion", value).apply()

    var themeMode: AppThemeMode
        get() {
            val name = prefs.getString("theme_mode", AppThemeMode.DARK_NAVY.name)
            return try {
                AppThemeMode.valueOf(name ?: AppThemeMode.DARK_NAVY.name)
            } catch (e: Exception) {
                AppThemeMode.DARK_NAVY
            }
        }
        set(value) = prefs.edit().putString("theme_mode", value.name).apply()

    // --- Bookmarks ---
    fun getBookmarkedPages(): Set<Int> {
        val raw = prefs.getStringSet("bookmarked_pages", emptySet()) ?: emptySet()
        return raw.mapNotNull { it.toIntOrNull() }.toSet()
    }

    fun toggleBookmark(pageNumber: Int): Boolean {
        val current = getBookmarkedPages().toMutableSet()
        val isNowBookmarked = if (current.contains(pageNumber)) {
            current.remove(pageNumber)
            false
        } else {
            current.add(pageNumber)
            true
        }
        prefs.edit().putStringSet("bookmarked_pages", current.map { it.toString() }.toSet()).apply()
        return isNowBookmarked
    }

    fun isPageBookmarked(pageNumber: Int): Boolean {
        return getBookmarkedPages().contains(pageNumber)
    }

    // --- Favorites ---
    fun getFavoritePromptIds(): Set<String> {
        return prefs.getStringSet("favorite_prompt_ids", emptySet()) ?: emptySet()
    }

    fun toggleFavoritePrompt(promptId: String): Boolean {
        val current = getFavoritePromptIds().toMutableSet()
        val isNowFav = if (current.contains(promptId)) {
            current.remove(promptId)
            false
        } else {
            current.add(promptId)
            true
        }
        prefs.edit().putStringSet("favorite_prompt_ids", current).apply()
        return isNowFav
    }

    fun isPromptFavorite(promptId: String): Boolean {
        return getFavoritePromptIds().contains(promptId)
    }

    // --- Custom Prompt Management ---
    fun getCustomPrompts(): List<PromptItem> {
        val raw = prefs.getString("custom_prompts_json", "[]") ?: "[]"
        val list = mutableListOf<PromptItem>()
        try {
            val array = JSONArray(raw)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    PromptItem(
                        id = obj.getString("id"),
                        globalNumber = obj.optInt("globalNumber", 1000 + i),
                        pageNumber = obj.optInt("pageNumber", 28),
                        category = obj.getString("category"),
                        title = obj.getString("title"),
                        type = obj.getString("type"),
                        text = obj.getString("text"),
                        imageUri = if (obj.has("imageUri") && !obj.isNull("imageUri")) obj.getString("imageUri") else null,
                        isCustom = true
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun saveCustomPrompt(prompt: PromptItem) {
        val existing = getCustomPrompts().toMutableList()
        val index = existing.indexOfFirst { it.id == prompt.id }
        if (index >= 0) {
            existing[index] = prompt
        } else {
            existing.add(prompt)
        }
        saveCustomPromptsList(existing)
    }

    fun deleteCustomPrompt(promptId: String) {
        val existing = getCustomPrompts().filter { it.id != promptId }
        saveCustomPromptsList(existing)
    }

    private fun saveCustomPromptsList(list: List<PromptItem>) {
        val array = JSONArray()
        for (item in list) {
            val obj = JSONObject().apply {
                put("id", item.id)
                put("globalNumber", item.globalNumber)
                put("pageNumber", item.pageNumber)
                put("category", item.category)
                put("title", item.title)
                put("type", item.type)
                put("text", item.text)
                put("imageUri", item.imageUri ?: JSONObject.NULL)
                put("isCustom", true)
            }
            array.put(obj)
        }
        prefs.edit().putString("custom_prompts_json", array.toString()).apply()
    }

    // --- Prompt Image Attachment (for any prompt, built-in or custom) ---
    fun getPromptImageUri(promptId: String): String? {
        // If it's a custom prompt, check custom prompts list first
        val custom = getCustomPrompts().find { it.id == promptId }
        if (custom?.imageUri != null) return custom.imageUri
        return prefs.getString("prompt_img_$promptId", null)
    }

    fun setPromptImageUri(promptId: String, imagePath: String?) {
        // Check if custom prompt
        val customList = getCustomPrompts().toMutableList()
        val customIndex = customList.indexOfFirst { it.id == promptId }
        if (customIndex >= 0) {
            val updated = customList[customIndex].copy(imageUri = imagePath)
            customList[customIndex] = updated
            saveCustomPromptsList(customList)
        }
        
        if (imagePath == null) {
            prefs.edit().remove("prompt_img_$promptId").apply()
        } else {
            prefs.edit().putString("prompt_img_$promptId", imagePath).apply()
        }
    }

    // --- Page Background Image Customization ---
    fun getPageBackgroundImage(pageNumber: Int): String? {
        return prefs.getString("page_bg_$pageNumber", null)
    }

    fun setPageBackgroundImage(pageNumber: Int, imagePath: String?) {
        if (imagePath == null) {
            prefs.edit().remove("page_bg_$pageNumber").apply()
        } else {
            prefs.edit().putString("page_bg_$pageNumber", imagePath).apply()
        }
    }

    // --- Helper to copy user picked image to permanent internal storage ---
    fun saveImageToInternalStorage(uri: Uri, prefix: String): String? {
        return try {
            val dir = File(context.filesDir, "user_images")
            if (!dir.exists()) dir.mkdirs()
            val file = File(dir, "${prefix}_${System.currentTimeMillis()}.jpg")
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
