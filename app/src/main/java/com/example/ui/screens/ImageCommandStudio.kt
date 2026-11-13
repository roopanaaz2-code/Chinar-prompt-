package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ImageCommand
import com.example.data.ImageCommandData

@Composable
fun ImageCommandStudio(onClose: () -> Unit) {
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }
    var selectedSubject by remember { mutableStateOf("All") }
    var expandedId by remember { mutableIntStateOf(-1) }

    val filteredCommands = remember(searchText, selectedSubject) {
        ImageCommandData.commands.filter { command ->
            (selectedSubject == "All" || command.subject == selectedSubject) &&
                (searchText.isBlank() ||
                    command.command.contains(searchText, ignoreCase = true) ||
                    command.title.contains(searchText, ignoreCase = true) ||
                    command.result.contains(searchText, ignoreCase = true) ||
                    command.prompt.contains(searchText, ignoreCase = true))
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Row(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("AI Image Command Studio", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                Text("1,000 prompt combinations • Small visual previews", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 8.dp),
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search commands") },
            placeholder = { Text("Search command, style, subject or effect") },
            shape = RoundedCornerShape(14.dp)
        )

        LazyRow(
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            items(ImageCommandData.subjects) { subject ->
                FilterChip(
                    selected = selectedSubject == subject,
                    onClick = { selectedSubject = subject },
                    label = { Text(subject, fontSize = 11.sp) }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 7.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("${filteredCommands.size} commands", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Tap a card to preview its full prompt", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(start = 12.dp, end = 12.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            items(filteredCommands, key = { it.id }) { command ->
                ImageCommandCard(
                    command = command,
                    expanded = expandedId == command.id,
                    onToggleExpanded = { expandedId = if (expandedId == command.id) -1 else command.id },
                    onCopyAndOpen = { copyAndOpenChatGPT(context, command) }
                )
            }
        }
    }
}

@Composable
private fun ImageCommandCard(
    command: ImageCommand,
    expanded: Boolean,
    onToggleExpanded: () -> Unit,
    onCopyAndOpen: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onToggleExpanded),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.Top) {
            CommandResultPreview(command, Modifier.size(width = 88.dp, height = 104.dp))
            Spacer(Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(command.command, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(3.dp))
                Text(command.title, fontSize = 13.sp, fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(command.result, fontSize = 10.sp, lineHeight = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 3, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(7.dp))
                Button(
                    onClick = onCopyAndOpen,
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 7.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB8862B), contentColor = Color(0xFF17120A))
                ) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(15.dp))
                    Spacer(Modifier.width(5.dp))
                    Text("Copy & Open ChatGPT", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        if (expanded) {
            Column(
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)).padding(horizontal = 14.dp, vertical = 11.dp)
            ) {
                Text("FULL GENERATION PROMPT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(5.dp))
                Text(command.prompt, fontSize = 12.sp, lineHeight = 17.sp)
                Spacer(Modifier.height(4.dp))
                Text("Tap “Copy & Open ChatGPT” to copy this prompt and open ChatGPT. Paste it there to generate the image.", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun CommandResultPreview(command: ImageCommand, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(12.dp)).background(
            Brush.linearGradient(listOf(Color(command.previewColor), Color(command.previewColor).copy(alpha = 0.72f), Color(0xFF182238)))
        ),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.size(58.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.12f)))
        Text(command.previewEmoji, fontSize = 34.sp)
        Text("PREVIEW", modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 6.dp), color = Color.White.copy(alpha = 0.92f), fontSize = 8.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
    }
}

private fun copyAndOpenChatGPT(context: Context, command: ImageCommand) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText(command.command, command.prompt))
    Toast.makeText(context, "Full prompt copied. Opening ChatGPT…", Toast.LENGTH_SHORT).show()
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://chatgpt.com/")).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    } catch (_: Exception) {
        Toast.makeText(context, "Prompt copied. Open ChatGPT and paste it.", Toast.LENGTH_LONG).show()
    }
}
