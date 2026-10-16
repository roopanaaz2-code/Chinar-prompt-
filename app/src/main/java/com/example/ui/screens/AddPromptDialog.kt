package com.example.ui.screens

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.BookRepository
import com.example.data.PreferencesManager
import com.example.data.PromptItem
import com.example.ui.theme.ChinarGold
import com.example.ui.theme.ChinarFlame
import java.io.File
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPromptDialog(
    prefs: PreferencesManager,
    defaultCategory: String = "Miniature Worlds",
    defaultPage: Int = 4,
    editPrompt: PromptItem? = null,
    onPromptAdded: (PromptItem) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var title by remember { mutableStateOf(editPrompt?.title ?: "") }
    var category by remember { mutableStateOf(editPrompt?.category ?: defaultCategory) }
    var isCategoryDropdownExpanded by remember { mutableStateOf(false) }
    var promptType by remember { mutableStateOf(editPrompt?.type ?: "IMAGE PROMPT") }
    var promptText by remember { mutableStateOf(editPrompt?.text ?: "") }
    var selectedImagePath by remember { mutableStateOf(editPrompt?.imageUri ?: editPrompt?.let { prefs.getPromptImageUri(it.id) }) }

    // Android Zero-permission Photo Picker
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            val savedPath = prefs.saveImageToInternalStorage(uri, "prompt_img")
            if (savedPath != null) {
                selectedImagePath = savedPath
                Toast.makeText(context, "Generated picture attached!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val categoryList = remember {
        BookRepository.categories.map { it.name }.plus("Custom Prompts")
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (editPrompt == null) "Add Custom Prompt" else "Edit Prompt",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Prompt Title") },
                placeholder = { Text("e.g. Cyberpunk Alley at Twilight") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Category Selector
            ExposedDropdownMenuBox(
                expanded = isCategoryDropdownExpanded,
                onExpandedChange = { isCategoryDropdownExpanded = !isCategoryDropdownExpanded }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoryDropdownExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(14.dp)
                )

                ExposedDropdownMenu(
                    expanded = isCategoryDropdownExpanded,
                    onDismissRequest = { isCategoryDropdownExpanded = false }
                ) {
                    categoryList.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                category = cat
                                isCategoryDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Prompt Type (Image vs Video)
            Text(
                text = "Prompt Type",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FilterChip(
                    selected = promptType == "IMAGE PROMPT",
                    onClick = { promptType = "IMAGE PROMPT" },
                    label = { Text("IMAGE PROMPT") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ChinarFlame,
                        selectedLabelColor = Color.White
                    )
                )
                FilterChip(
                    selected = promptType == "VIDEO PROMPT",
                    onClick = { promptType = "VIDEO PROMPT" },
                    label = { Text("VIDEO PROMPT") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF4CC9F0),
                        selectedLabelColor = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Prompt Text
            OutlinedTextField(
                value = promptText,
                onValueChange = { promptText = it },
                label = { Text("Full Prompt Text") },
                placeholder = { Text("Create an ultra-realistic cinematic 9:16 vertical image of...") },
                minLines = 4,
                maxLines = 8,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Generated Picture Attachment
            Text(
                text = "Attach Result / Generated Picture",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            if (selectedImagePath != null) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = if (selectedImagePath!!.startsWith("android.resource://")) Uri.parse(selectedImagePath!!) else File(selectedImagePath!!),
                            contentDescription = "Attached Result",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        IconButton(
                            onClick = { selectedImagePath = null },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                                .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove", tint = Color.White)
                        }
                    }
                }
            } else {
                OutlinedButton(
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, tint = ChinarGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Select from Gallery", color = MaterialTheme.colorScheme.onSurface)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Save CTA
            Button(
                onClick = {
                    if (title.isBlank() || promptText.isBlank()) {
                        Toast.makeText(context, "Please enter both a title and prompt text", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    val catObj = BookRepository.categories.find { it.name == category }
                    val pageNum = if (editPrompt == null) catObj?.startPage ?: defaultPage else editPrompt.pageNumber
                    val savedPrompt = PromptItem(
                        id = editPrompt?.id ?: "custom_${UUID.randomUUID().toString().take(8)}",
                        globalNumber = editPrompt?.globalNumber ?: 120 + prefs.getCustomPrompts().size,
                        pageNumber = pageNum,
                        category = category,
                        title = title.trim(),
                        type = promptType,
                        text = promptText.trim(),
                        imageUri = selectedImagePath,
                        isCustom = editPrompt?.isCustom ?: true
                    )
                    if (savedPrompt.isCustom) prefs.saveCustomPrompt(savedPrompt) else prefs.savePromptOverride(savedPrompt)
                    onPromptAdded(savedPrompt)
                    Toast.makeText(context, if (editPrompt == null) "Custom prompt saved to book!" else "Prompt updated offline!", Toast.LENGTH_SHORT).show()
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ChinarGold,
                    contentColor = Color(0xFF1E1000)
                )
            ) {
                Text(
                    text = if (editPrompt == null) "Save Prompt to Book" else "Save Changes",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}
