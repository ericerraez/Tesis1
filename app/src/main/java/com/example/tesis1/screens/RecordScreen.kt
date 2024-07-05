package com.example.tesis1.screens

import ApiService
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tesis1.components.NavBar
import com.example.tesis1.ui.theme.surfaceContainerDark
import com.example.tesis1.ui.theme.surfaceDimLight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordScreen(navController: NavHostController, historyTitle: String, recordTitle: String) {
    val transcriptions = remember { mutableStateOf(listOf<Pair<String, String>>()) }
    var currentTime by remember { mutableStateOf(LocalTime.now()) }
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalTime.now()
            delay(1000)
        }
    }

    val apiService = remember {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.16:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().apply {
                connectTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
                readTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
            }.build())
            .build()
            .create(ApiService::class.java)
    }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val response = apiService.getLastTranscription().execute()
                    if (response.isSuccessful) {
                        response.body()?.let { responseBody ->
                            val responseBodyString = responseBody.string()
                            val jsonObject = JSONObject(responseBodyString)
                            val user = jsonObject.getString("user")
                            val text = jsonObject.getString("text")
                            transcriptions.value = listOf(Pair(user, text))
                            isLoading = false
                        }
                    } else {
                        errorMessage = "Error: ${response.code()} - ${response.message()}"
                        isLoading = false
                    }
                }
            } catch (e: Exception) {
                errorMessage = "Exception: ${e.message}"
                isLoading = false
            }
        }
    }

    Surface(
        color = surfaceDimLight,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header Row
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = surfaceContainerDark
                    )
                }
                Text(
                    text = recordTitle,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(start = 8.dp),
                    color = surfaceContainerDark
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /* TODO: Search action */ }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = surfaceContainerDark
                    )
                }
                IconButton(onClick = { /* TODO: More options */ }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "More",
                        tint = surfaceContainerDark
                    )
                }
            }

            if (isLoading) {
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                // Transcriptions List
                transcriptions.value.forEach { (user, transcription) ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "$user:",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = transcription,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currentTime.format(DateTimeFormatter.ofPattern("HH:mm a")),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            NavBar(currentScreen = "History", navController = navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewRecordScreen() {
    RecordScreen(navController = rememberNavController(), historyTitle = "Marketing 1", recordTitle = "Record 1")
}
