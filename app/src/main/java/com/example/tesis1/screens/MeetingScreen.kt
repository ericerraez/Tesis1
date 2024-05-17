package com.example.tesis1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
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
import com.example.tesis1.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

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
    val participants = mutableListOf("Participant 1", "Participant 2", "Participant 3", "Participant 4", "Participant 5",
        "Participant 6", "Participant 7", "Participant 8", "Participant 9", "Participant 10", "Participant 11", "Participant 12",
        "Participant 13", "Participant 14", "Participant 15", "Participant 16", "Participant 17", "Participant 18", "Participant 19", "Participant 20",
        "Participant 21", "Participant 22", "Participant 23", "Participant 24", "Participant 25")

    Surface(
        color = surfaceDimLight,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
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

            val rows = (participants.size + 1) / 3 + 1

            repeat(rows) { rowIndex ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (rowIndex == 0) {
                        Spacer(modifier = Modifier.width(5.dp))
                        CircularCard(title = "ANI", modifier = Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(8.dp))
                        if (participants.isNotEmpty()) {
                            SquareCard(title = participants[0], modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        if (participants.size > 1) {
                            SquareCard(title = participants[1], modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    } else {
                        val startParticipantIndex = (rowIndex - 1) * 3 + 2
                        val endParticipantIndex = min(startParticipantIndex + 3, participants.size)
                        for (i in startParticipantIndex until endParticipantIndex) {
                            Spacer(modifier = Modifier.width(8.dp))
                            SquareCard(title = participants[i], modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { /* Lógica de onClick aquí */ }
                    .background(
                        color = primaryLight,
                        shape = RoundedCornerShape(30.dp))
                    .padding(start = 32.dp, top = 24.dp, end = 40.dp, bottom = 24.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Mic,
                    contentDescription = "Voice Icon",
                    tint = onPrimaryLight
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Speak",
                    style = MaterialTheme.typography.labelLarge,
                    color = onPrimaryLight
                )
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
