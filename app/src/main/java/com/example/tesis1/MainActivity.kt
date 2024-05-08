package com.example.tesis1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tesis1.screens.RoomItem
import com.example.tesis1.ui.theme.surfaceLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(onRoomSelected: (String) -> Unit) {
    Surface(color = surfaceLight) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var searchText by remember { mutableStateOf("") }
            com.example.tesis1.screens.SearchBar(
                searchText = searchText,
                onSearchTextChange = { searchText = it })
            Spacer(modifier = Modifier.height(16.dp))

            val rooms = listOf("Marketing 1", "Marketing 2", "Marketing 3", "Marketing 4", "Marketing 5")
            val filteredRooms = if (searchText.isBlank()) rooms else rooms.filter { it.contains(searchText, ignoreCase = true) }

            filteredRooms.forEach { room ->
                RoomItem(roomName = room, lastInteractionTime = "12:34 PM", onClick = { onRoomSelected(room) })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
