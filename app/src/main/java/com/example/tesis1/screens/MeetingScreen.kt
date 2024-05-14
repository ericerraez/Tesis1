package com.example.tesis1.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tesis1.components.CircularCard
import com.example.tesis1.components.SquareCard
import com.example.tesis1.ui.theme.surfaceContainerDark
import com.example.tesis1.ui.theme.surfaceDimLight
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MeetingScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val elapsedTime = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {
                delay(1000)
                elapsedTime.intValue++
            }
        }
    }

    val minutes = elapsedTime.intValue / 60
    val seconds = elapsedTime.intValue % 60

    Surface(
        color = surfaceDimLight,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                BackArrow(
                    modifier = Modifier.padding(bottom = 16.dp),
                    onClick = { navController.navigateUp() }
                )
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "$minutes min $seconds sec",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = surfaceContainerDark
                )
                Spacer(modifier = Modifier.weight(1f))

                ThreeDotsIcon(
                    modifier = Modifier.padding(bottom = 16.dp),
                    onClick = { /* Handle three dots icon click */ }
                )
            }
            Text(
                text = "Meeting Title",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp),
                color = surfaceContainerDark
            )
            Text(
                text = "This is a small description of the meeting.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp),
                color = surfaceContainerDark
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                CircularCard(title = "ANI")
                Spacer(modifier = Modifier.width(8.dp))
                SquareCard(title = "Square Card 1")
                Spacer(modifier = Modifier.width(8.dp))
                SquareCard(title = "Square Card 2")
                Spacer(modifier = Modifier.width(8.dp))
                SquareCard(title = "Square Card 3")
            }
        }
    }
}

@Composable
fun BackArrow(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Icon(
        imageVector = Icons.Filled.ArrowBack,
        contentDescription = "Back",
        tint = surfaceContainerDark,
        modifier = modifier.clickable(onClick = onClick)
    )
}

@Composable
fun ThreeDotsIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Icon(
        imageVector = Icons.Filled.MoreVert,
        contentDescription = "More",
        tint = surfaceContainerDark,
        modifier = modifier.clickable(onClick = onClick)
    )
}

@Preview
@Composable
fun MeetingScreenPreview() {
    MeetingScreen(navController = NavHostController(LocalContext.current))
}
