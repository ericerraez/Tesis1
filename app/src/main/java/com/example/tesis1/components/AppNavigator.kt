package com.example.tesis1.components

import TopicsScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tesis1.components.Destinations.HomeScreen
import com.example.tesis1.screens.HistoryScreen
import com.example.tesis1.screens.HomeScreen
import com.example.tesis1.screens.SettingsScreen
import com.example.tesis1.screens.TopicsScreen

object Destinations {
    const val HomeScreen = "home_screen"
    const val HistoryScreen = "history_screen"
    const val TopicsScreen = "topics_screen"
    const val SettingsScreen = "settings_screen"
}

@Composable
fun AppNavigator(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destinations.HomeScreen,
        modifier = modifier
    ) {
        composable(Destinations.HomeScreen) {
            HomeScreen(navController = navController)
        }
        composable(Destinations.HistoryScreen) {
            HistoryScreen(onRoomSelected = { roomName ->
                navController.navigate("${Destinations.TopicsScreen}/$roomName")
            })
        }
        composable("${Destinations.TopicsScreen}/{roomName}") { backStackEntry ->
            TopicsScreen(
                roomName = backStackEntry.arguments?.getString("roomName") ?: "Unknown",
                onBack = { navController.popBackStack() }
            )
        }

        }
}
