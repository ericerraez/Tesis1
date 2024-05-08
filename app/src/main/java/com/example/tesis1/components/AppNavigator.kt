package com.example.tesis1.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tesis1.HistoryScreen
import com.example.tesis1.screens.TopicsScreen

object Destinations {
    const val HistoryScreen = "history_screen"
    const val TopicsScreen = "topics_screen"
}
//
@Composable
fun AppNavigator(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destinations.HistoryScreen,
        modifier = modifier
    ) {
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
