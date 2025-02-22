package com.example.assignment.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController

// 🎨 Enhanced Dark Theme Colors
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    secondary = SecondaryGreen,
    tertiary = AccentGold,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    primaryContainer = DarkPrimaryContainer,
    secondaryContainer = DarkSecondaryContainer
)

// 🎨 Enhanced Light Theme Colors
private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = SecondaryGreen,
    tertiary = AccentGold,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    primaryContainer = LightPrimaryContainer,
    secondaryContainer = LightSecondaryContainer
)

@Composable
fun AssignmentTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // ✨ System UI Controller for better immersive experience
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = colorScheme.background, darkIcons = !isDarkTheme
        )
    }

    // 🔥 Apply the modernized theme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = ModernTypography,
        shapes = ModernShapes,
        content = content
    )
}
