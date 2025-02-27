package com.example.assignment.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.assignment.data.UserPreferences
import com.example.assignment.network.AlphaVantageService
import com.example.assignment.screens.MainScreen
import com.example.assignment.screens.NewsScreen
import com.example.assignment.screens.TransactionScreen
import com.example.assignment.screens.UserProfileScreen
import com.example.assignment.viewmodel.StockViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    userPreferences: UserPreferences,
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    stockViewModel: StockViewModel,
    alphaVantageService: AlphaVantageService
) {
    NavHost(
        navController = navController,
        startDestination = "main_screen",
        modifier = modifier
    ) {
        composable("main_screen") {
            // MainScreen now expects the network version of AlphaVantageService.
            MainScreen(alphaVantageService = alphaVantageService)
        }
        composable("profile_screen") {
            UserProfileScreen(userPreferences = userPreferences)
        }
        composable("transaction_screen") {
            TransactionScreen()
        }
        composable("news_screen") {
            NewsScreen()
        }
    }
}
