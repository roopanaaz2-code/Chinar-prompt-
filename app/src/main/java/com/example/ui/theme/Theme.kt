package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.data.AppThemeMode

private val DarkNavyColorScheme = darkColorScheme(
    primary = ChinarGold,
    onPrimary = Color(0xFF1E1000),
    primaryContainer = Color(0xFF4A2800),
    onPrimaryContainer = Color(0xFFFFDDB3),
    secondary = AccentPurple,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF3B1E6D),
    onSecondaryContainer = Color(0xFFE9D5FF),
    tertiary = AccentCyan,
    background = DarkNavyBackground,
    onBackground = DarkNavyTextPrimary,
    surface = DarkNavySurface,
    onSurface = DarkNavyTextPrimary,
    surfaceVariant = DarkNavySurfaceVariant,
    onSurfaceVariant = DarkNavyTextSecondary,
    outline = DarkNavyBorder
)

private val ParchmentColorScheme = lightColorScheme(
    primary = ChinarCrimson,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDAD4),
    onPrimaryContainer = Color(0xFF3B0900),
    secondary = Color(0xFF70529A),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFEEDDFF),
    onSecondaryContainer = Color(0xFF280D52),
    tertiary = Color(0xFF00687A),
    background = ParchmentBackground,
    onBackground = ParchmentTextPrimary,
    surface = ParchmentSurface,
    onSurface = ParchmentTextPrimary,
    surfaceVariant = ParchmentSurfaceVariant,
    onSurfaceVariant = ParchmentTextSecondary,
    outline = ParchmentBorder
)

private val AmoledColorScheme = darkColorScheme(
    primary = ChinarGold,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF332000),
    onPrimaryContainer = Color(0xFFFFDDB3),
    secondary = AccentPurple,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF291054),
    onSecondaryContainer = Color(0xFFE9D5FF),
    tertiary = AccentCyan,
    background = AmoledBackground,
    onBackground = Color(0xFFF1F5F9),
    surface = AmoledSurface,
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = AmoledSurfaceVariant,
    onSurfaceVariant = Color(0xFF94A3B8),
    outline = AmoledBorder
)

@Composable
fun ChinarBookTheme(
    themeMode: AppThemeMode = AppThemeMode.DARK_NAVY,
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeMode) {
        AppThemeMode.DARK_NAVY -> DarkNavyColorScheme
        AppThemeMode.LIGHT_PARCHMENT -> ParchmentColorScheme
        AppThemeMode.AMOLED_MIDNIGHT -> AmoledColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
