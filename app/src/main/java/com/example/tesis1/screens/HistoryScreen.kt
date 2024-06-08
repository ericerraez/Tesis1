package com.example.tesis1.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tesis1.components.CustomCard
import com.example.tesis1.components.SearchBar
import com.example.tesis1.ui.theme.surfaceDimLight

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryScreen(navController: NavHostController, topicTitles: List<String>) {
    var searchText by remember { mutableStateOf("") }
    var selectedRoom by remember { mutableStateOf<String?>(null) }

    val roomTitles = listOf(
        "Marketing 1",
        "Marketing 2",
        "Marketing 3",
        "Marketing 4",
        "Marketing 5",
        "Marketing 6",
        "Marketing 7",
        "Marketing 8",
        "Marketing 9",
        "Marketing 10",
        "Marketing 11",
        "Marketing 12",
    )
    val topicTitles = topicTitles
    Surface(color = surfaceDimLight) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SearchBar(
                searchText = searchText,
                onSearchTextChanged = { text ->
                    searchText = text
                    selectedRoom = null
                },
                roomTitles = roomTitles,
                topicTitles = topicTitles,
                onSearchRoomSelected = { room ->
                    selectedRoom = room
                },
                onSearchTopicSelected = {
                }
            )

            Spacer(modifier = Modifier.height(1.dp))

            val filteredRoomTitles = if (selectedRoom != null) {
                listOf(selectedRoom!!)
            } else {
                roomTitles.filter {
                    it.contains(searchText, ignoreCase = true)
                }
            }
            Column(Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
            ) {
                filteredRoomTitles.forEach { roomTitle ->
                    CustomCard(
                        navController,
                        roomTitle = roomTitle,
                        roomSubtitle = "Estrategias Marketing",
                        searchText = searchText
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
