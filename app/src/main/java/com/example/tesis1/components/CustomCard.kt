package com.example.tesis1.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tesis1.ui.theme.*

@Composable
fun CustomCard(navController: NavController, title: String) {
    Surface(
        color = surfaceLight,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("topics")
                }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = surfaceContainerDark
                )

                Text(
                    text = "Hola, soy de software",
                    style = MaterialTheme.typography.labelSmall,
                    color = surfaceContainerDark
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "10:00am",
                style = MaterialTheme.typography.labelSmall,
                color = surfaceContainerDark
            )
        }
    }
}
