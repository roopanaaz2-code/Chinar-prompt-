package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BookRepository
import com.example.data.PromptItem
import com.example.ui.theme.ChinarGold
import com.example.ui.theme.ChinarFlame

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateToPage: (Int) -> Unit,
    onClose: () -> Unit,
    isPromptFavorite: (String) -> Boolean,
    onToggleFavoritePrompt: (String) -> Unit
) {
    val context = LocalContext.current
    var query by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("ALL") }

    val filteredResults by remember(query, selectedFilter) {
        derivedStateOf {
            val q = query.trim().lowercase()
            val prefs = com.example.data.PreferencesManager(context)
            val allItems = BookRepository.allPrompts.map { prefs.getPromptOverride(it.id) ?: it } + prefs.getCustomPrompts()
            allItems.filter { prompt ->
                val matchesType = when (selectedFilter) {
                    "IMAGE" -> prompt.type == "IMAGE PROMPT"
                    "VIDEO" -> prompt.type == "VIDEO PROMPT"
                    else -> true
                }
                val matchesQuery = if (q.isEmpty()) {
                    true
                } else {
                    prompt.title.lowercase().contains(q) ||
                    prompt.text.lowercase().contains(q) ||
                    prompt.category.lowercase().contains(q)
                }
                matchesType && matchesQuery
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Search Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search 119 prompts, categories, words...") },
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = ChinarGold)
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = ChinarGold,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        // Filter chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedFilter == "ALL",
                    onClick = { selectedFilter = "ALL" },
                    label = { Text("All (${BookRepository.allPrompts.size})") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ChinarGold,
                        selectedLabelColor = Color(0xFF1E1000)
                    )
                )
            }
            item {
                FilterChip(
                    selected = selectedFilter == "IMAGE",
                    onClick = { selectedFilter = "IMAGE" },
                    label = { Text("Images (${BookRepository.allPrompts.count { it.type == \"IMAGE PROMPT\" }})") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ChinarFlame,
                        selectedLabelColor = Color.White
                    )
                )
            }
            item {
                FilterChip(
                    selected = selectedFilter == "VIDEO",
                    onClick = { selectedFilter = "VIDEO" },
                    label = { Text("Videos (${BookRepository.allPrompts.count { it.type == \"VIDEO PROMPT\" }})") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF4CC9F0),
                        selectedLabelColor = Color.Black
                    )
                )
            }
        }

        // Result count indicator
        Text(
            text = "${filteredResults.size} prompt${if (filteredResults.size != 1) "s" else ""} found",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
        )

        // Results list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredResults) { prompt ->
                SearchResultCard(
                    prompt = prompt,
                    query = query,
                    isFavorite = isPromptFavorite(prompt.id),
                    onToggleFavorite = { onToggleFavoritePrompt(prompt.id) },
                    onJumpToPage = {
                        onNavigateToPage(prompt.pageNumber)
                        onClose()
                    },
                    onCopy = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("AI Prompt", prompt.text)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "Prompt copied!", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun SearchResultCard(
    prompt: PromptItem,
    query: String,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onJumpToPage: () -> Unit,
    onCopy: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onJumpToPage() }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = ChinarGold.copy(alpha = 0.2f),
                    shape = CircleShape,
                    modifier = Modifier.size(24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "${prompt.globalNumber}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ChinarGold
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = prompt.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${prompt.category} • Page ${prompt.pageNumber}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(onClick = onToggleFavorite, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) ChinarGold else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(onClick = onCopy, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy",
                        tint = ChinarGold,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Highlighted text preview
            Text(
                text = highlightMatch(prompt.text, query),
                fontSize = 12.sp,
                lineHeight = 17.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3
            )
        }
    }
}

fun highlightMatch(text: String, query: String) = buildAnnotatedString {
    if (query.isBlank()) {
        append(text)
        return@buildAnnotatedString
    }
    val lowerText = text.lowercase()
    val lowerQuery = query.lowercase()
    var start = 0
    while (true) {
        val index = lowerText.indexOf(lowerQuery, start)
        if (index < 0) {
            append(text.substring(start))
            break
        }
        append(text.substring(start, index))
        withStyle(SpanStyle(background = Color(0xFFFFB703).copy(alpha = 0.4f), fontWeight = FontWeight.Bold)) {
            append(text.substring(index, index + lowerQuery.length))
        }
        start = index + lowerQuery.length
    }
}
