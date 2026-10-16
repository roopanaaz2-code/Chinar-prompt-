package com.example.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.BookRepository
import com.example.data.PreferencesManager
import com.example.data.PromptItem
import com.example.ui.components.PageTurnContainer
import com.example.ui.pages.PageDispatcher
import com.example.ui.theme.ChinarGold
import com.example.ui.theme.ChinarFlame

@Composable
fun BookReaderScreen(
    prefs: PreferencesManager,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var currentPage by remember { mutableIntStateOf(prefs.lastReadPage) }
    var fontScale by remember { mutableFloatStateOf(prefs.fontScale) }
    var reducedMotion by remember { mutableStateOf(prefs.reducedMotion) }
    var themeMode by remember { mutableStateOf(prefs.themeMode) }

    var bookmarkedPages by remember { mutableStateOf(prefs.getBookmarkedPages()) }
    var favoritePromptIds by remember { mutableStateOf(prefs.getFavoritePromptIds()) }
    var customPrompts by remember { mutableStateOf(prefs.getCustomPrompts()) }
    var pageBgRefreshKey by remember { mutableIntStateOf(0) }

    var isDistractionFree by remember { mutableStateOf(false) }
    var showTocSheet by remember { mutableStateOf(false) }
    var showSearchScreen by remember { mutableStateOf(false) }
    var showBookmarksScreen by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var showAddPromptDialog by remember { mutableStateOf(false) }
    var showPageBgSheet by remember { mutableStateOf(false) }
    var isQuickSliderActive by remember { mutableStateOf(false) }

    val totalPages = BookRepository.pages.size
    val activePageObj = BookRepository.pages.find { it.pageNumber == currentPage } ?: BookRepository.pages.first()
    val isCurrentBookmarked = bookmarkedPages.contains(currentPage)

    fun navigateTo(page: Int) {
        val target = page.coerceIn(1, totalPages)
        currentPage = target
        prefs.lastReadPage = target
    }

    // Handle system back
    BackHandler(enabled = showSearchScreen || showBookmarksScreen || showTocSheet || showSettingsDialog || showAddPromptDialog || showPageBgSheet) {
        when {
            showAddPromptDialog -> showAddPromptDialog = false
            showPageBgSheet -> showPageBgSheet = false
            showSearchScreen -> showSearchScreen = false
            showBookmarksScreen -> showBookmarksScreen = false
            showTocSheet -> showTocSheet = false
            showSettingsDialog -> showSettingsDialog = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            topBar = {
                AnimatedVisibility(
                    visible = !isDistractionFree,
                    enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Logo and Title
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable { showTocSheet = true }
                                    .padding(4.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.chinar_tree_logo),
                                    contentDescription = "Chinar Logo",
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .border(1.dp, ChinarGold, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Text(
                                        text = "Chinar Prompts",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "p. $currentPage of $totalPages",
                                        fontSize = 10.sp,
                                        color = ChinarGold
                                    )
                                }
                            }

                            // Reader Action Icons
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                // Add Prompt Button
                                IconButton(onClick = { showAddPromptDialog = true }, modifier = Modifier.size(36.dp)) {
                                    Icon(Icons.Default.Add, contentDescription = "Add Custom Prompt", tint = ChinarGold)
                                }

                                // Change Background Button
                                IconButton(onClick = { showPageBgSheet = true }, modifier = Modifier.size(36.dp)) {
                                    Icon(Icons.Default.Wallpaper, contentDescription = "Change Page Background", tint = MaterialTheme.colorScheme.onSurface)
                                }

                                // Bookmark Button
                                IconButton(
                                    onClick = {
                                        val nowBookmarked = prefs.toggleBookmark(currentPage)
                                        bookmarkedPages = prefs.getBookmarkedPages()
                                        Toast.makeText(
                                            context,
                                            if (nowBookmarked) "Page $currentPage bookmarked" else "Bookmark removed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isCurrentBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                        contentDescription = "Bookmark Page",
                                        tint = if (isCurrentBookmarked) ChinarGold else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                // Search
                                IconButton(onClick = { showSearchScreen = true }, modifier = Modifier.size(36.dp)) {
                                    Icon(Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.onSurface)
                                }

                                // Favorites
                                IconButton(onClick = { showBookmarksScreen = true }, modifier = Modifier.size(36.dp)) {
                                    Icon(Icons.Default.Star, contentDescription = "Saved", tint = ChinarGold)
                                }

                                // TOC
                                IconButton(onClick = { showTocSheet = true }, modifier = Modifier.size(36.dp)) {
                                    Icon(Icons.Default.MenuBook, contentDescription = "Contents", tint = MaterialTheme.colorScheme.onSurface)
                                }

                                // Settings
                                IconButton(onClick = { showSettingsDialog = true }, modifier = Modifier.size(36.dp)) {
                                    Icon(Icons.Default.Settings, contentDescription = "Settings", tint = MaterialTheme.colorScheme.onSurface)
                                }
                            }
                        }

                        // Thin Progress Bar
                        LinearProgressIndicator(
                            progress = { currentPage.toFloat() / totalPages.toFloat() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.5.dp),
                            color = ChinarGold,
                            trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
                        )
                    }
                }
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = !isDistractionFree,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.background.copy(alpha = 0.95f)
                                    )
                                )
                            )
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        if (isQuickSliderActive) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("p. 1", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Slider(
                                    value = currentPage.toFloat(),
                                    onValueChange = { navigateTo(it.toInt()) },
                                    valueRange = 1f..totalPages.toFloat(),
                                    steps = totalPages - 2,
                                    colors = SliderDefaults.colors(
                                        thumbColor = ChinarGold,
                                        activeTrackColor = ChinarGold
                                    ),
                                    modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
                                )
                                Text("p. $totalPages", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        // Floating Pill Bar
                        Surface(
                            shape = RoundedCornerShape(24.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                            shadowElevation = 8.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = { navigateTo(currentPage - 1) },
                                    enabled = currentPage > 1
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Previous Page",
                                        tint = if (currentPage > 1) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clickable { isQuickSliderActive = !isQuickSliderActive }
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "Page $currentPage / $totalPages",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = ChinarGold
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isQuickSliderActive) "▲" else "▼",
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                IconButton(onClick = { isDistractionFree = true }) {
                                    Icon(
                                        Icons.Default.Fullscreen,
                                        contentDescription = "Distraction-free Reading",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                IconButton(
                                    onClick = { navigateTo(currentPage + 1) },
                                    enabled = currentPage < totalPages
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = "Next Page",
                                        tint = if (currentPage < totalPages) ChinarGold else MaterialTheme.colorScheme.outline
                                    )
                                }
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .clickable {
                        if (isDistractionFree) {
                            isDistractionFree = false
                        }
                    }
            ) {
                // Read custom background for current page
                val customBg = remember(currentPage, pageBgRefreshKey) {
                    prefs.getPageBackgroundImage(currentPage)
                }

                PageTurnContainer(
                    currentPage = currentPage,
                    totalPages = totalPages,
                    reducedMotion = reducedMotion,
                    onNavigateNext = { navigateTo(currentPage + 1) },
                    onNavigatePrev = { navigateTo(currentPage - 1) },
                    modifier = Modifier.fillMaxSize()
                ) { pageNumber ->
                    val pageObj = BookRepository.pages.find { it.pageNumber == pageNumber } ?: BookRepository.pages.first()
                    val pageCustomPrompts = customPrompts.filter {
                        it.pageNumber == pageNumber || (pageNumber in 4..27 && it.category == pageObj.category)
                    }
                    val pageBg = prefs.getPageBackgroundImage(pageNumber)

                    PageDispatcher(
                        page = pageObj,
                        fontScale = fontScale,
                        isBookmarked = bookmarkedPages.contains(pageNumber),
                        prefs = prefs,
                        customPromptsForPage = pageCustomPrompts,
                        customBackgroundPath = pageBg,
                        onToggleBookmark = {
                            prefs.toggleBookmark(pageNumber)
                            bookmarkedPages = prefs.getBookmarkedPages()
                        },
                        onNavigateToPage = { navigateTo(it) },
                        isPromptFavorite = { prefs.isPromptFavorite(it) },
                        onToggleFavoritePrompt = {
                            prefs.toggleFavoritePrompt(it)
                            favoritePromptIds = prefs.getFavoritePromptIds()
                        },
                        onOpenAddPrompt = { showAddPromptDialog = true },
                        onOpenBackgroundCustomizer = { showPageBgSheet = true },
                        onPromptImageUpdated = { promptId, newPath ->
                            customPrompts = prefs.getCustomPrompts()
                        },
                        onDeleteCustomPrompt = { promptId ->
                            prefs.deleteCustomPrompt(promptId)
                            customPrompts = prefs.getCustomPrompts()
                            Toast.makeText(context, "Prompt deleted", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                if (isDistractionFree) {
                    FloatingActionButton(
                        onClick = { isDistractionFree = false },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(20.dp),
                        containerColor = ChinarGold,
                        contentColor = Color(0xFF1E1000)
                    ) {
                        Icon(Icons.Default.FullscreenExit, contentDescription = "Exit Fullscreen")
                    }
                }
            }
        }

        // Add Custom Prompt Dialog
        if (showAddPromptDialog) {
            AddPromptDialog(
                prefs = prefs,
                defaultCategory = activePageObj.category,
                defaultPage = currentPage,
                onPromptAdded = { newPrompt ->
                    customPrompts = prefs.getCustomPrompts()
                },
                onDismiss = { showAddPromptDialog = false }
            )
        }

        // Page Background Customizer Sheet
        if (showPageBgSheet) {
            PageBackgroundSheet(
                pageNumber = currentPage,
                currentBgPath = prefs.getPageBackgroundImage(currentPage),
                prefs = prefs,
                onBackgroundChanged = {
                    pageBgRefreshKey++
                },
                onDismiss = { showPageBgSheet = false }
            )
        }

        // Table of Contents Sheet
        if (showTocSheet) {
            TableOfContentsSheet(
                currentPage = currentPage,
                onSelectPage = { navigateTo(it) },
                onDismiss = { showTocSheet = false }
            )
        }

        // Search Overlay Screen
        if (showSearchScreen) {
            SearchScreen(
                onNavigateToPage = { navigateTo(it) },
                onClose = { showSearchScreen = false },
                isPromptFavorite = { prefs.isPromptFavorite(it) },
                onToggleFavoritePrompt = {
                    prefs.toggleFavoritePrompt(it)
                    favoritePromptIds = prefs.getFavoritePromptIds()
                }
            )
        }

        // Bookmarks & Favorites Screen
        if (showBookmarksScreen) {
            BookmarksFavoritesScreen(
                bookmarkedPages = bookmarkedPages,
                favoritePromptIds = favoritePromptIds,
                onToggleBookmark = {
                    prefs.toggleBookmark(it)
                    bookmarkedPages = prefs.getBookmarkedPages()
                },
                onToggleFavoritePrompt = {
                    prefs.toggleFavoritePrompt(it)
                    favoritePromptIds = prefs.getFavoritePromptIds()
                },
                onNavigateToPage = { navigateTo(it) },
                onClose = { showBookmarksScreen = false }
            )
        }

        // Settings Dialog
        if (showSettingsDialog) {
            SettingsDialog(
                currentTheme = themeMode,
                onSelectTheme = {
                    themeMode = it
                    prefs.themeMode = it
                },
                fontScale = fontScale,
                onFontScaleChange = {
                    fontScale = it
                    prefs.fontScale = it
                },
                reducedMotion = reducedMotion,
                onReducedMotionChange = {
                    reducedMotion = it
                    prefs.reducedMotion = it
                },
                currentPage = currentPage,
                totalPages = totalPages,
                onDismiss = { showSettingsDialog = false }
            )
        }
    }
}
