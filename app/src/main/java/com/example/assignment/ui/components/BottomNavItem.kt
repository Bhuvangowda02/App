package com.example.assignment.ui.components

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
    val hasBadge: Boolean = false,  // ✅ Shows a badge if true
    val badgeCount: Int? = null      // ✅ Shows number if not null
)
