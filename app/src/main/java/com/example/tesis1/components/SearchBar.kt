package com.example.tesis1.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.clearCompositionErrors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tesis1.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    onSearchTextChanged: (String) -> Unit
) {
    val searchTextState = remember { mutableStateOf(TextFieldValue()) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        color = outlineVariantLight
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                tint = surfaceContainerDark,
                modifier = Modifier.padding(8.dp)
            )
            TextField(
                value = searchTextState.value,
                onValueChange = {
                    searchTextState.value = it
                    onSearchTextChanged(it.text)
                },
                placeholder = { Text("Search") },
                singleLine = true,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(onSearchTextChanged = {})
}
