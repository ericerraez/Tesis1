package com.example.tesis1.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tesis1.components.CustomCard
import com.example.tesis1.components.SearchBar
import com.example.tesis1.ui.theme.*



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RoomScreen(navController: NavHostController) {
    var searchText by remember { mutableStateOf("") }
    Surface(color = surfaceDimLight) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            SearchBar(onSearchTextChanged = { text -> searchText = text })

            Spacer(modifier = Modifier.height(1.dp))

            CustomCard(navController, title = "Software", searchText = searchText)
            CustomCard(navController, title = "Marketing", searchText = searchText)
            CustomCard(navController, title = "Design", searchText = searchText)
            CustomCard(navController, title = "Nursing", searchText = searchText)

        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun RoomScreenPreview() {
    RoomScreen(navController = NavHostController(LocalContext.current))}