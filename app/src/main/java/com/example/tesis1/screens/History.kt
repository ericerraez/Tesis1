package com.example.tesis1.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VoiceChat
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tesis1.ui.theme.AppTheme
import com.example.tesis1.ui.theme.surfaceLight
import kotlinx.coroutines.launch

@Preview
@Composable
fun PreviewRoomListScreen() {
    AppTheme {
        MainScreen()
    }
}

@Composable
fun MainScreen() {
    val scope = rememberCoroutineScope()
    val (currentRoute, setCurrentRoute) = remember { mutableStateOf("Rooms") }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(currentRoute = currentRoute, onRouteSelected = { route ->
                scope.launch {
                    setCurrentRoute(route)
                }
            })
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            RoomListScreen()
        }
    }
}

@Composable
fun RoomListScreen() {
    Surface(color = surfaceLight) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var searchText by remember { mutableStateOf("") }
            SearchBar(searchText = searchText, onSearchTextChange = { searchText = it })
            Spacer(modifier = Modifier.height(16.dp))

            val rooms = listOf(
                "Marketing 1",
                "Marketing 2",
                "Marketing 4",
                "Marketing 5",
                "Marketing 6"
            )
            val filteredRooms = if (searchText.isBlank()) rooms else rooms.filter { it.contains(searchText, ignoreCase = true) }

            filteredRooms.forEach { room ->
                RoomItem(roomName = room, lastInteractionTime = "12:34 PM")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun RoomItem(roomName: String, lastInteractionTime: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Room Image",
            modifier = Modifier.size(40.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = roomName,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = lastInteractionTime,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchText: String, onSearchTextChange: (String) -> Unit) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        placeholder = { Text("Search in History") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(16.dp),
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions.Default,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}


@Composable
fun BottomNavigationBar(currentRoute: String, onRouteSelected: (String) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        NavigationBarItem(
            selected = currentRoute == "Rooms",
            onClick = { onRouteSelected("Rooms") },
            label = { Text("Rooms") },
            icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Rooms Icon") }
        )
        NavigationBarItem(
            selected = currentRoute == "History",
            onClick = { onRouteSelected("History") },
            label = { Text("History") },
            icon = { Icon(Icons.Filled.VoiceChat, contentDescription = "Settings Icon") }
        )
        NavigationBarItem(
            selected = currentRoute == "Settings",
            onClick = { onRouteSelected("Settings") },
            label = { Text("Settings") },
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings Icon") }
        )
    }
}
