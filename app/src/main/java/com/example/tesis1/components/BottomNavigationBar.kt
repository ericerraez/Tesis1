package com.example.tesis1.components
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomNavigationBar(currentRoute: String, onRouteSelected: (String) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            label = { Text("Rooms") },
            selected = currentRoute == "Rooms",
            onClick = { onRouteSelected("Rooms") },
            modifier = Modifier.weight(1f)
        )
        NavigationBarItem(
            label = { Text("History") },
            selected = currentRoute == "History",
            onClick = { onRouteSelected("History") },
            modifier = Modifier.weight(1f)
        )
        NavigationBarItem(
            label = { Text("Settings") },
            selected = currentRoute == "Settings",
            onClick = { onRouteSelected("Settings") },
            modifier = Modifier.weight(1f)
        )
    }
}

fun NavigationBarItem(
    label: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {

}
