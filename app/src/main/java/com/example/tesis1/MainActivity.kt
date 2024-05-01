package com.example.tesis1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tesis1.screens.HistoryScreen
import com.example.tesis1.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "historyScreen", Modifier.padding(innerPadding)) {
            composable("historyScreen") {
                // Pass a lambda that navigates to the topics screen
                HistoryScreen(onNavigateToTopics = { roomName ->
                    navController.navigate("topicsScreen/$roomName")
                })
            }
            composable("topicsScreen/{roomName}") { backStackEntry ->
                // Get the roomName from the back stack entry
                val roomName = backStackEntry.arguments?.getString("roomName") ?: "Unknown"
                TopicsScreen(roomName = roomName)
            }
        }
    }
}

@Composable
fun TopicsScreen(roomName: String) {
    // Dummy composable for the topics screen
    Text(text = "Topics for $roomName", style = MaterialTheme.typography.headlineMedium)
}
