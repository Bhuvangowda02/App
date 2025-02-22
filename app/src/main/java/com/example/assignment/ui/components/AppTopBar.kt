package com.example.assignment.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(title: String = "Stock Market Tracker", isDarkTheme: Boolean, onThemeToggle: (Boolean) -> Unit) {
    TopAppBar(
        title = { Text(title) },
        actions = {
            IconButton(onClick = { onThemeToggle(!isDarkTheme) }) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Filled.Brightness7 else Icons.Filled.Brightness4,
                    contentDescription = "Toggle Theme"
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}
