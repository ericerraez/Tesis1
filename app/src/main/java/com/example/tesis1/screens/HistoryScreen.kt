package com.example.tesis1.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tesis1.R
import com.example.tesis1.components.SearchBar
import com.example.tesis1.ui.theme.AppTheme

@Composable
fun HistoryScreen(navController: NavController) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var searchText by remember { mutableStateOf("") }
            SearchBar(onSearchTextChanged = { searchText = it })
            Spacer(modifier = Modifier.height(16.dp))

            val historyItems = listOf(
                HistoryData("Marketing 1", "Estrategias de Marketing Digital", "10 members", "9:40 AM", painterResource(id = R.drawable.ic_launcher_foreground)),
                HistoryData("Marketing 2", "Estrategias de Marketing Digital", "10 members", "9:40 AM", painterResource(id = R.drawable.ic_launcher_foreground)),
                HistoryData("Marketing 3", "Estrategias de Marketing Digital", "10 members", "9:40 AM", painterResource(id = R.drawable.ic_launcher_foreground)),
                HistoryData("Marketing 4", "Estrategias de Marketing Digital", "10 members", "9:40 AM", painterResource(id = R.drawable.ic_launcher_foreground)),
                HistoryData("Marketing 5", "Estrategias de Marketing Digital", "10 members", "9:40 AM", painterResource(id = R.drawable.ic_launcher_foreground))
            )

            val filteredItems = historyItems.filter { it.title.contains(searchText, ignoreCase = true) }

            LazyColumn {
                items(filteredItems) { item ->
                    HistoryItem(item, onItemClick = {
                        navController.navigate("topics/${item.title}")
                    })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

data class HistoryData(
    val title: String,
    val subtitle: String,
    val members: String,
    val time: String,
    val image: Painter
)

@Composable
fun HistoryItem(item: HistoryData, onItemClick: (HistoryData) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onItemClick(item) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = item.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 8.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${item.members} • ${item.subtitle}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = item.time,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHistoryScreen() {
    AppTheme {
        HistoryScreen(navController = rememberNavController())
    }
}
