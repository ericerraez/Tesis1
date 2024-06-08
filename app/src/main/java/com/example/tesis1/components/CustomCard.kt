package com.example.tesis1.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.tesis1.ui.theme.*
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCard(
    navController: NavController,
    roomTitle: String,
    roomSubtitle: String,
    searchText: String,
) {
    var currentTime by remember { mutableStateOf(LocalTime.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalTime.now()
            delay(1000)
        }
    }
    if (roomTitle.contains(searchText, ignoreCase = true) || searchText.isBlank()) {
        Surface(
            color = surfaceDimLight,
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
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null,
                    tint = surfaceContainerDark,
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = roomTitle,
                        style = MaterialTheme.typography.headlineMedium,
                        color = surfaceContainerDark
                    )

                    Text(
                        text = roomSubtitle,
                        style = MaterialTheme.typography.labelSmall,
                        color = surfaceContainerDark
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = currentTime.format(DateTimeFormatter.ofPattern("HH:mm a")),
                    style = MaterialTheme.typography.labelSmall,
                    color = surfaceContainerDark
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCardTopics(
    navController: NavController,
    topicTitle: String,
    topicSubtitle: String,
    searchText: String
) {
    var currentTime by remember { mutableStateOf(LocalTime.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalTime.now()
            delay(1000)
        }
    }
    if (topicTitle.contains(searchText, ignoreCase = true) || searchText.isBlank()) {
        Surface(
            color = surfaceDimLight,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("meeting")
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null,
                    tint = surfaceContainerDark,
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = topicTitle,
                        style = MaterialTheme.typography.headlineSmall,
                        color = surfaceContainerDark
                    )

                    Text(
                        text = topicSubtitle,
                        style = MaterialTheme.typography.labelSmall,
                        color = surfaceContainerDark
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = currentTime.format(DateTimeFormatter.ofPattern("HH:mm a")),
                    style = MaterialTheme.typography.labelSmall,
                    color = surfaceContainerDark
                )
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun CustomCardTopicsPreview() {
    CustomCard(
        navController = NavController(LocalContext.current),
        roomTitle = "",
        roomSubtitle = "",
        searchText = "")
    }
