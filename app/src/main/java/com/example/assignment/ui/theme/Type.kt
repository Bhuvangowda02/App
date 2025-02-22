package com.example.assignment.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// 📝 Modern Typography Settings
val ModernTypography = Typography(
    displayLarge = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold),
    displayMedium = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
    displaySmall = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold),

    headlineLarge = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Medium),
    headlineMedium = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
    headlineSmall = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),

    bodyLarge = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
    bodyMedium = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal),
    bodySmall = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal)
)
