package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.data.PreferencesManager
import com.example.ui.screens.BookReaderScreen
import com.example.ui.theme.ChinarBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val prefs = PreferencesManager(applicationContext)

        setContent {
            var currentTheme by remember { mutableStateOf(prefs.themeMode) }

            ChinarBookTheme(themeMode = currentTheme) {
                BookReaderScreen(
                    prefs = prefs,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
