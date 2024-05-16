package com.example.tesis1.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tesis1.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun RecordScreen() {
    var transcription by remember { mutableStateOf("") }

    AppTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = transcription)
            // Llama a fetchTranscription cuando se inicia la pantalla
            LaunchedEffect(Unit) {
                fetchTranscription { transcription = it }
            }
        }
    }
}

fun fetchTranscription(onTranscriptionFetched: (String) -> Unit) {
    val url = URL("http://localhost:8000/transcriptor/convert/")
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doInput = true

            val inputStream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))
            val response = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }
            reader.close()

            val jsonResponse = JSONObject(response.toString())
            val transcription = jsonResponse.getString("text")
            withContext(Dispatchers.Main) {
                onTranscriptionFetched(transcription)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecordScreen() {
    RecordScreen()
}
