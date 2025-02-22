package com.example.assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.assignment.data.ThemePreferences
import com.example.assignment.data.UserPreferences
import com.example.assignment.navigation.AppNavigation
import com.example.assignment.ui.components.AppTopBar
import com.example.assignment.ui.components.BottomNavItem
import com.example.assignment.ui.components.BottomNavigationBar
import com.example.assignment.ui.theme.AssignmentTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var themePreferences: ThemePreferences
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themePreferences = ThemePreferences(this)
        userPreferences = UserPreferences(this)

        setContent {
            val isDarkTheme = remember { mutableStateOf(false) }

            // Collect theme setting changes
            LaunchedEffect(Unit) {
                lifecycleScope.launch {
                    themePreferences.themeFlow.collect { theme ->
                        isDarkTheme.value = theme
                    }
                }
            }

            AssignmentTheme(isDarkTheme.value) {
                MainContent(
                    isDarkTheme = isDarkTheme.value,
                    onThemeToggle = { newTheme ->
                        lifecycleScope.launch {
                            themePreferences.saveTheme(newTheme)
                            isDarkTheme.value = newTheme
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun MainContent(isDarkTheme: Boolean, onThemeToggle: (Boolean) -> Unit) {
        val navController = rememberNavController()
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        val title = remember(currentRoute) { getTitleForRoute(currentRoute) } // Dynamically set AppBar title

        val bottomNavItems = listOf(
            BottomNavItem("Home", Icons.Filled.Home, "main_screen"),
            BottomNavItem("Transactions", Icons.Filled.Article, "transaction_screen"),
            BottomNavItem("Profile", Icons.Filled.Person, "profile_screen"),
            BottomNavItem("News", Icons.Filled.Article, "news_screen")
        )

        Scaffold(
            topBar = {
                AppTopBar(title = title, isDarkTheme = isDarkTheme, onThemeToggle = onThemeToggle)
            },
            bottomBar = {
                BottomNavigationBar(navController, bottomNavItems)
            }
        ) { innerPadding ->
            AppNavigation(
                navController = navController,
                userPreferences = userPreferences,
                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }

    private fun getTitleForRoute(route: String?): String {
        return when (route) {
            "main_screen" -> "Home"
            "transaction_screen" -> "Transactions"
            "profile_screen" -> "Profile"
            "news_screen" -> "News"
            else -> "Stock Market Tracker"
        }
    }
}
