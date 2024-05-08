package com.example.tesis1.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tesis1.ui.theme.AppTheme
import com.example.tesis1.ui.theme.surfaceLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicsScreen(roomName: String, onBack: () -> Unit) {
    AppTheme {
        Surface(color = surfaceLight) {
            Column(modifier = Modifier.fillMaxSize()) {
                TopAppBar(
                    title = { Text(text = roomName) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = surfaceLight
                    )
                )
                Divider()
                LazyColumn {
                    items(getTopics()) { topic ->
                        TopicItem(topicName = topic.first, topicTime = topic.second)
                    }
                }
            }
        }
    }
}

@Composable
fun TopicItem(topicName: String, topicTime: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profile Image",
            modifier = Modifier.size(40.dp)
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = topicName,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = topicTime,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

fun getTopics(): List<Pair<String, String>> = listOf(
    "Estrategias de Marketing" to "09:00 AM",
    "Marketing Digital" to "10:15 AM",
    "SEO y Optimización de Contenido" to "11:30 AM",
    "Publicidad en Redes Sociales" to "01:45 PM",
    "Análisis de Datos en Marketing" to "03:00 PM"
)

@Preview(showBackground = true)
@Composable
fun PreviewTopicsScreen() {
    TopicsScreen(roomName = "Marketing 1", onBack = {})
}
