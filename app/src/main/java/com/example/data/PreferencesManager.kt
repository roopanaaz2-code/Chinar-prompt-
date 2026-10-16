package com.example.data

import android.content.Context
import android.content.SharedPreferences

enum class AppThemeMode {
    DARK_NAVY,
    LIGHT_PARCHMENT,
    AMOLED_MIDNIGHT
}

class PreferencesManager(context: Context) {
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
}
