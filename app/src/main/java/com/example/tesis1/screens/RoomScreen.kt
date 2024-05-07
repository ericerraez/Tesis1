package com.example.tesis1.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tesis1.components.CustomCard
import com.example.tesis1.components.SearchBar
import com.example.tesis1.ui.theme.*



@Composable
fun RoomScreen(navController: NavHostController) {
    Surface(color = surfaceDimLight) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SearchBar(onSearchTextChanged = {})

            Spacer(modifier = Modifier.height(1.dp))

            CustomCard(navController, title = "Software")
            CustomCard(navController, title = "Marketing")
            CustomCard(navController, title = "Design")
            CustomCard(navController, title = "Nursing")

        }
    }
}
@Preview
@Composable
fun RoomScreenPreview() {
    RoomScreen(navController = NavHostController(LocalContext.current))}