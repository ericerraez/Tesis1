package com.example.tesis1.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tesis1.ApiService
import com.example.tesis1.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.json.JSONObject
import retrofit2.http.GET

@Composable
fun RecordScreen() {
    var transcription = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        Log.d("RecordScreen", "LaunchedEffect triggered")
        coroutineScope.launch {
            fetchLastTranscription { transcriptionText ->
                Log.d("RecordScreen", "Transcription fetched: $transcriptionText")
                transcription.value = transcriptionText
            }
        }
    }

    AppTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = transcription.value)
        }
    }
}

fun fetchLastTranscription(onTranscriptionFetched: (String) -> Unit) {
    Log.d("fetchLastTranscription", "Started fetching last transcription")
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.56.1:8000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    val apiService = retrofit.create(ApiService::class.java)
    apiService.getLastTranscription().enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            Log.d("fetchLastTranscription", "Received response: $response")
            if (response.isSuccessful) {
                 val responseBody = response.body()?.string()
                val jsonResponse = responseBody?.let { JSONObject(it) }
                val transcription = jsonResponse?.getString("text") ?: "No transcription found"
                onTranscriptionFetched(transcription)
            } else {
                onTranscriptionFetched("Failed to fetch transcription")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.e("fetchLastTranscription", "Failed to fetch transcription: ${t.message}")
            onTranscriptionFetched("Error: ${t.message}")
        }
    })
}

interface ApiService {
    @GET("get_last_transcription/")
    fun getLastTranscription(): Call<ResponseBody>
}

@Preview(showBackground = true)
@Composable
fun PreviewRecordScreen() {
    RecordScreen()
}
