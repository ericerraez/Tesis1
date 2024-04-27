package com.example.tesis1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.tesis1.components.BottomNavigationBar
import com.example.tesis1.screens.RoomListScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
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
            when (currentRoute) {
                "Rooms" -> RoomListScreen()
                "History" -> Text("History Content")
                "Settings" -> Text("Settings Content")
            }
        }
    }
}
