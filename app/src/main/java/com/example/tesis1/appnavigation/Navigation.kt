package com.example.tesis1.appnavigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tesis1.screens.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val topicTitles = listOf("Estrategias Marketing", "Diseño de Interfaces", "Desarrollo de Software", "Cuidado del Ambiente", "Cuidado Canino")
    val historyTopicTitles = listOf("Estrategias Marketing", "Diseño de Interfaces", "Desarrollo de Software", "Cuidado del Ambiente")

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = "room",
                modifier = Modifier.weight(1f)
            ) {
                composable("login") { LoginScreen(navController) }
                composable("room") { RoomScreen(navController, topicTitles = topicTitles) }
                composable("topics") { RoomTopics(navController, roomTitle = "Default Room", topicTitles = topicTitles) }
                composable("meeting") { MeetingScreen(navController) }
                composable("history") { HistoryScreen(navController, historyTopicTitles = historyTopicTitles) }
                composable("topicsHistory") { TopicsScreen(navController, historyTitle = "Default History", historyTopicTitles = historyTopicTitles) }
            }
        }
    }
}
