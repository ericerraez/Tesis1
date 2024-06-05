package com.example.tesis1.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.tesis1.components.NavBar
import com.example.tesis1.ui.theme.AppTheme

@Preview
@Composable
fun PreviewHistoryScreen() {
    AppTheme {
        HistoryScreen(navController = NavHostController(LocalContext.current))
    }
}

@Composable
fun HistoryScreen(navController: NavHostController) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var searchText by remember { mutableStateOf("") }
            SearchBar(searchText = searchText, onSearchTextChange = { searchText = it })
            Spacer(modifier = Modifier.height(16.dp))

            val historyItems = listOf(
                "Marketing 1",
                "Marketing 2",
                "Marketing 3",
                "Marketing 4",
                "Marketing 5"
            )

            historyItems.forEach { item ->
                HistoryItem(item)
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.weight(1f))

            NavBar(currentScreen = "History", navController = navController)
        }
    }
}

@Composable
fun HistoryItem(item: String) {
    Text(
        text = item,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchText: String, onSearchTextChange: (String) -> Unit) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        placeholder = { Text("Search history") },
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
