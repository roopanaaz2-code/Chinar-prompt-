package com.example.ui.pages

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.BookPage
import com.example.data.BookRepository
import com.example.data.PageType
import com.example.data.PreferencesManager
import com.example.data.PromptItem
import com.example.ui.screens.ImageViewerDialog
import com.example.ui.theme.ChinarGold
import com.example.ui.theme.ChinarCrimson
import com.example.ui.theme.ChinarFlame
import java.io.File

private fun categoryImageRes(categoryId: String): Int = when (categoryId) {
    "miniature" -> R.drawable.category_1
    "architecture" -> R.drawable.category_2
    "portraits" -> R.drawable.category_3
    "nature" -> R.drawable.category_4
    "scifi" -> R.drawable.category_5
    "food_product" -> R.drawable.category_6
    "fantasy" -> R.drawable.category_7
    "travel" -> R.drawable.category_8
    "tech" -> R.drawable.category_9
    "vehicles" -> R.drawable.category_10
    "creator" -> R.drawable.category_11
    "motion" -> R.drawable.category_12
    else -> R.drawable.category_overview
}

@Composable
fun PageDispatcher(
    page: BookPage,
    fontScale: Float,
    isBookmarked: Boolean,
    prefs: PreferencesManager,
    customPromptsForPage: List<PromptItem>,
    customBackgroundPath: String?,
    onToggleBookmark: () -> Unit,
    onNavigateToPage: (Int) -> Unit,
    isPromptFavorite: (String) -> Boolean,
    onToggleFavoritePrompt: (String) -> Unit,
    onOpenAddPrompt: () -> Unit,
    onOpenBackgroundCustomizer: () -> Unit,
    onEditPrompt: (PromptItem) -> Unit,
    onPromptImageUpdated: (promptId: String, newPath: String?) -> Unit,
    onDeleteCustomPrompt: (promptId: String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Custom Page Background if set by user
        if (customBackgroundPath != null) {
            AsyncImage(
                model = File(customBackgroundPath),
                contentDescription = "Custom Page Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Legibility overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Black.copy(alpha = 0.70f),
                                Color.Black.copy(alpha = 0.85f)
                            )
                        )
                    )
            )
        }

        // Page content
        when (page.pageType) {
            is PageType.Cover -> {
                CoverPageView(
                    onStartReading = { onNavigateToPage(2) },
                    onOpenToc = { onNavigateToPage(2) },
                    onOpenBackgroundCustomizer = onOpenBackgroundCustomizer
                )
            }
            is PageType.HowToAndToc1 -> {
                HowToAndToc1View(
                    fontScale = fontScale,
                    onNavigateToPage = onNavigateToPage,
                    onOpenBackgroundCustomizer = onOpenBackgroundCustomizer
                )
            }
            is PageType.Toc2 -> {
                Toc2View(
                    fontScale = fontScale,
                    onNavigateToPage = onNavigateToPage,
                    onOpenBackgroundCustomizer = onOpenBackgroundCustomizer
                )
            }
            is PageType.Prompts -> {
                PromptsPageView(
                    page = page,
                    fontScale = fontScale,
                    prefs = prefs,
                    customPrompts = customPromptsForPage,
                    isPromptFavorite = isPromptFavorite,
                    onToggleFavoritePrompt = onToggleFavoritePrompt,
                    onOpenAddPrompt = onOpenAddPrompt,
                    onOpenBackgroundCustomizer = onOpenBackgroundCustomizer,
                    onEditPrompt = onEditPrompt,
                    onPromptImageUpdated = onPromptImageUpdated,
                    onDeleteCustomPrompt = onDeleteCustomPrompt
                )
            }
            is PageType.Checklist -> {
                ChecklistPageView(
                    fontScale = fontScale,
                    onReturnToCover = { onNavigateToPage(1) },
                    onOpenToc = { onNavigateToPage(2) },
                    onOpenBackgroundCustomizer = onOpenBackgroundCustomizer
                )
            }
        }
    }
}

@Composable
fun CoverPageView(
    onStartReading: () -> Unit,
    onOpenToc: () -> Unit,
    onOpenBackgroundCustomizer: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))

            // Ornate Hero Image Banner
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .border(1.5.dp, ChinarGold.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.img_chinar_hero),
                        contentDescription = "Chinar Book Cover Artwork",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.65f)
                                    )
                                )
                            )
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.chinar_tree_logo),
                            contentDescription = "Chinar Tree Logo",
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .border(1.dp, ChinarGold, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "ROYAL CHINAR EDITION",
                            color = ChinarGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "THE MODERN AI\nPROMPT BOOK",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp,
                    lineHeight = 34.sp
                ),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "100+ Ready-to-Use IMAGE & VIDEO Prompts",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = ChinarFlame,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "This book published by Salman Udhas",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = ChinarGold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "A practical collection for photorealism, cinematic scenes, miniature worlds, product shots, travel, creators, and motion.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ChinarGold.copy(alpha = 0.3f))
            ) {
                Text(
                    text = "VERTICAL-FIRST • COPY • CUSTOMIZE • CREATE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ChinarGold,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatCard(count = "28", label = "Pages", color = ChinarGold)
                StatCard(count = "119+", label = "Prompts", color = ChinarFlame)
                StatCard(count = "12", label = "Categories", color = ChinarCrimson)
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onStartReading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ChinarGold,
                    contentColor = Color(0xFF1E1000)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Icon(Icons.Default.MenuBook, contentDescription = null)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Start Reading Book",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onOpenToc,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = ChinarGold, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Contents", color = MaterialTheme.colorScheme.onBackground, fontSize = 13.sp)
                }

                OutlinedButton(
                    onClick = onOpenBackgroundCustomizer,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.Wallpaper, contentDescription = null, tint = ChinarFlame, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Change BG", color = MaterialTheme.colorScheme.onBackground, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun StatCard(count: String, label: String, color: Color) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.width(96.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = color
            )
            Text(
                text = label,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun HowToAndToc1View(
    fontScale: Float,
    onNavigateToPage: (Int) -> Unit,
    onOpenBackgroundCustomizer: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "How to Use This Book",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                IconButton(onClick = onOpenBackgroundCustomizer) {
                    Icon(Icons.Default.Wallpaper, contentDescription = "Change Background", tint = ChinarGold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    BulletItem("Choose a prompt that matches your scene and copy the full prompt into your image or video generator.", fontScale)
                    BulletItem("Most image prompts are designed for vertical 9:16 social content. Change the aspect ratio for 4:5, 3:4, 1:1, or 16:9 when needed.", fontScale)
                    BulletItem("For a personal photo, add: “Use the uploaded reference image for identity; preserve the person's face, facial features, skin tone, and recognizable identity. Change only the explicitly requested elements.”", fontScale)
                    BulletItem("For video, specify duration, shot progression, continuous character appearance, natural motion, and exact dialogue when speech is needed.", fontScale)
                    BulletItem("AI tools vary. Exact text, fine fingers, complex architecture, and lip-sync may need retries or a dedicated editing pass.", fontScale)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, ChinarGold.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = ChinarGold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Reusable Quality Add-On",
                            fontWeight = FontWeight.Bold,
                            color = ChinarGold,
                            fontSize = 15.sp * fontScale
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Photorealistic materials, physically plausible lighting and shadows, natural anatomy, coherent perspective, consistent scale, crisp subject focus, subtle depth of field, professional color grading, no unintended text, no logos, no watermark, no duplicated objects, no distorted hands or faces.",
                        fontSize = 13.sp * fontScale,
                        lineHeight = 19.sp * fontScale,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Contents — Part 1",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        items(BookRepository.categories.take(6)) { cat ->
            CategoryCardItem(category = cat, onNavigateToPage = onNavigateToPage)
            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun Toc2View(
    fontScale: Float,
    onNavigateToPage: (Int) -> Unit,
    onOpenBackgroundCustomizer: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Contents — Part 2",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                IconButton(onClick = onOpenBackgroundCustomizer) {
                    Icon(Icons.Default.Wallpaper, contentDescription = "Change Background", tint = ChinarGold)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Tap any category to jump directly to its first page in the book.",
                fontSize = 13.sp * fontScale,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        items(BookRepository.categories.drop(6)) { cat ->
            CategoryCardItem(category = cat, onNavigateToPage = onNavigateToPage)
            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToPage(28) }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = ChinarFlame)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Quick Customization Checklist",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Page 28 • Pro tips for prompting",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "Page 28 →",
                        fontWeight = FontWeight.Bold,
                        color = ChinarFlame,
                        fontSize = 13.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun CategoryCardItem(
    category: com.example.data.CategoryMeta,
    onNavigateToPage: (Int) -> Unit
) {
    val catColor = Color(category.colorHex)
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToPage(category.startPage) }
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = categoryImageRes(category.id)),
                contentDescription = "${category.name} category artwork",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(58.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, catColor.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(catColor)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${category.promptCount} Prompts • Pages ${category.startPage}–${category.endPage}",
                    fontSize = 12.sp,
                    color = catColor
                )
                Text(
                    text = category.description,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
            Text(
                text = "p. ${category.startPage} →",
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = catColor
            )
        }
    }
}

@Composable
fun BulletItem(text: String, fontScale: Float) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text("• ", fontWeight = FontWeight.Bold, color = ChinarGold, fontSize = 14.sp * fontScale)
        Text(
            text = text,
            fontSize = 13.sp * fontScale,
            lineHeight = 18.sp * fontScale,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun PromptsPageView(
    page: BookPage,
    fontScale: Float,
    prefs: PreferencesManager,
    customPrompts: List<PromptItem>,
    isPromptFavorite: (String) -> Boolean,
    onToggleFavoritePrompt: (String) -> Unit,
    onOpenAddPrompt: () -> Unit,
    onOpenBackgroundCustomizer: () -> Unit,
    onEditPrompt: (PromptItem) -> Unit,
    onPromptImageUpdated: (promptId: String, newPath: String?) -> Unit,
    onDeleteCustomPrompt: (promptId: String) -> Unit
) {
    val categoryMeta = BookRepository.categories.find { it.name == page.category }
    val categoryColor = categoryMeta?.let { Color(it.colorHex) } ?: ChinarGold

    val combinedPrompts = remember(page.prompts, customPrompts) {
        page.prompts.map { prefs.getPromptOverride(it.id) ?: it } + customPrompts
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        item {
            // Category Badge Header with Add Prompt & Change BG icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    color = categoryColor.copy(alpha = 0.18f),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, categoryColor.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = page.category.uppercase(),
                        color = categoryColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onOpenAddPrompt, modifier = Modifier.size(34.dp)) {
                        Icon(Icons.Default.Add, contentDescription = "Add Custom Prompt", tint = ChinarGold)
                    }
                    IconButton(onClick = onOpenBackgroundCustomizer, modifier = Modifier.size(34.dp)) {
                        Icon(Icons.Default.Wallpaper, contentDescription = "Change Background", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text(
                        text = "PAGE ${page.pageNumber}",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
        }

        items(combinedPrompts) { prompt ->
            val attachedImg = prompt.imageUri ?: prefs.getPromptImageUri(prompt.id)

            PromptCard(
                prompt = prompt,
                categoryColor = categoryColor,
                fontScale = fontScale,
                attachedImagePath = attachedImg,
                prefs = prefs,
                isFavorite = isPromptFavorite(prompt.id),
                onToggleFavorite = { onToggleFavoritePrompt(prompt.id) },
                onImageUpdated = { newPath -> onPromptImageUpdated(prompt.id, newPath) },
                onEdit = { onEditPrompt(prompt) },
                onDelete = if (prompt.isCustom) { { onDeleteCustomPrompt(prompt.id) } } else null
            )
            Spacer(modifier = Modifier.height(14.dp))
        }

        item {
            // Inline button to add custom prompt
            OutlinedButton(
                onClick = onOpenAddPrompt,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = ChinarGold)
                Spacer(modifier = Modifier.width(8.dp))
                Text("+ Add Custom Prompt to ${page.category}", color = MaterialTheme.colorScheme.onSurface)
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun PromptCard(
    prompt: PromptItem,
    categoryColor: Color,
    fontScale: Float,
    attachedImagePath: String?,
    prefs: PreferencesManager,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onImageUpdated: (String?) -> Unit,
    onEdit: () -> Unit,
    onDelete: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var copiedRecently by remember { mutableStateOf(false) }
    var viewingFullImage by remember { mutableStateOf(false) }

    // Photo picker to attach or replace generated picture
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            val savedPath = prefs.saveImageToInternalStorage(uri, "prompt_${prompt.id}")
            if (savedPath != null) {
                prefs.setPromptImageUri(prompt.id, savedPath)
                onImageUpdated(savedPath)
                Toast.makeText(context, "Generated picture attached to prompt!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (viewingFullImage && attachedImagePath != null) {
        ImageViewerDialog(
            imagePath = attachedImagePath,
            onDismiss = { viewingFullImage = false }
        )
    }

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row: Global # + Title + Type + Star + Delete
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = categoryColor.copy(alpha = 0.2f),
                    shape = CircleShape,
                    modifier = Modifier.size(28.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "${prompt.globalNumber}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = categoryColor
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = prompt.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp * fontScale,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        if (prompt.isCustom) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = ChinarGold.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "USER",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ChinarGold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                    Text(
                        text = prompt.type,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (prompt.type == "VIDEO PROMPT") Color(0xFF4CC9F0) else categoryColor,
                        letterSpacing = 0.8.sp
                    )
                }

                IconButton(onClick = onToggleFavorite, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) ChinarGold else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(onClick = onEdit, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Prompt",
                        tint = ChinarGold,
                        modifier = Modifier.size(18.dp)
                    )
                }

                if (onDelete != null) {
                    IconButton(onClick = onDelete, modifier = Modifier.size(36.dp)) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Custom Prompt",
                            tint = Color.Red.copy(alpha = 0.7f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Attached Generated Result Picture Thumbnail (if available)
            if (attachedImagePath != null) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .clickable { viewingFullImage = true }
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = if (attachedImagePath.startsWith("android.resource://")) {
                                Uri.parse(attachedImagePath)
                            } else {
                                File(attachedImagePath)
                            },
                            contentDescription = "Generated AI Result",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        // Label badge
                        Surface(
                            color = Color.Black.copy(alpha = 0.65f),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Visibility, contentDescription = null, tint = ChinarGold, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Generated Result (Tap to view)", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // Remove image button
                        IconButton(
                            onClick = {
                                prefs.setPromptImageUri(prompt.id, null)
                                onImageUpdated(null)
                                Toast.makeText(context, "Attached picture removed", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(6.dp)
                                .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                                .size(28.dp)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Remove Picture", tint = Color.White, modifier = Modifier.size(14.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Prompt text container
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = prompt.text,
                    fontSize = 13.sp * fontScale,
                    lineHeight = 19.sp * fontScale,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(14.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action row: Copy Prompt button, Attach Picture, Share
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("AI Prompt", prompt.text)
                        clipboard.setPrimaryClip(clip)
                        copiedRecently = true
                        Toast.makeText(context, "Prompt copied to clipboard!", Toast.LENGTH_SHORT).show()
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (copiedRecently) Color(0xFF10B981) else categoryColor,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.height(40.dp).weight(1f)
                ) {
                    Icon(
                        imageVector = if (copiedRecently) Icons.Default.Check else Icons.Default.ContentCopy,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (copiedRecently) "Copied!" else "Copy Prompt",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Attach / Change Picture Button
                OutlinedButton(
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Icon(
                        imageVector = if (attachedImagePath != null) Icons.Default.Image else Icons.Default.AddPhotoAlternate,
                        contentDescription = "Attach Picture",
                        modifier = Modifier.size(16.dp),
                        tint = ChinarGold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (attachedImagePath != null) "Pic" else "Add Pic",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                OutlinedButton(
                    onClick = {
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "${prompt.title}\n\n${prompt.text}")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, "Share Prompt")
                        context.startActivity(shareIntent)
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun ChecklistPageView(
    fontScale: Float,
    onReturnToCover: () -> Unit,
    onOpenToc: () -> Unit,
    onOpenBackgroundCustomizer: () -> Unit
) {
    val checklistItems = remember {
        listOf(
            "Subject: Who or what is the main focus?",
            "Setting: Where does the scene happen?",
            "Action: What is the subject doing?",
            "Composition: Close-up, medium shot, wide shot, top-down, or three-quarter view?",
            "Lighting: Golden hour, soft daylight, studio light, neon night, or candlelight?",
            "Style: Documentary, luxury advertising, cinematic film still, macro photography, or architectural editorial?",
            "Output: Aspect ratio, duration for video, resolution target, and platform.",
            "Consistency: Preserve identity, clothing, props, geometry, and lighting across frames.",
            "Negative constraints: Remove unwanted text, logos, watermarks, extra limbs, distorted hands, and duplicated objects."
        )
    }

    val checkedStates = remember { mutableStateMapOf<Int, Boolean>() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Quick Customization Checklist",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                IconButton(onClick = onOpenBackgroundCustomizer) {
                    Icon(Icons.Default.Wallpaper, contentDescription = "Change Background", tint = ChinarGold)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Use this checklist before generating each prompt to fine-tune your parameters and guarantee consistent quality.",
                fontSize = 13.sp * fontScale,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        items(checklistItems.indices.toList()) { index ->
            val isChecked = checkedStates[index] ?: false
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isChecked) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { checkedStates[index] = !isChecked }
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isChecked) Icons.Outlined.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                        contentDescription = null,
                        tint = if (isChecked) ChinarGold else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = checklistItems[index],
                        fontSize = 13.sp * fontScale,
                        lineHeight = 18.sp * fontScale,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(18.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, ChinarGold.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "💡 Pro Tip",
                        fontWeight = FontWeight.Bold,
                        color = ChinarGold,
                        fontSize = 15.sp * fontScale
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Better prompts describe a coherent scene rather than piling on unrelated details. For video, ask for a few clear actions and a simple camera move instead of many rapid events.",
                        fontSize = 13.sp * fontScale,
                        lineHeight = 19.sp * fontScale,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onOpenToc,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Table of Contents")
                }
                Button(
                    onClick = onReturnToCover,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ChinarGold, contentColor = Color(0xFF1E1000))
                ) {
                    Text("Back to Cover")
                }
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
