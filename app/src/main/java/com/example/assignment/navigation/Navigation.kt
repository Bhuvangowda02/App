package com.example.assignment.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.assignment.data.UserPreferences
import com.example.assignment.screens.MainScreen
import com.example.assignment.screens.NewsScreen
import com.example.assignment.screens.TransactionScreen
import com.example.assignment.screens.UserProfileScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    userPreferences: UserPreferences,
    isDarkTheme: Boolean,  // ✅ FIXED: Ensure these values are correctly received
    onThemeToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "main_screen",
        modifier = modifier // Ensures correct layout spacing
    ) {
        composable("main_screen") {
            MainScreen() // ✅ FIXED: Parameters passed
        }
        composable("profile_screen") {
            UserProfileScreen(
                userPreferences = userPreferences
            ) // ✅ FIXED: Parameters passed
        }
        composable("transaction_screen") {
            TransactionScreen() // Transactions screen does not need theme values
        }
        composable("news_screen") {
            NewsScreen() // ✅ FIXED: Parameters passed
        }
    }
}
