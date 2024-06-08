package com.example.tesis1

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tesis1.screens.HistoryScreen
import com.example.tesis1.screens.LoginScreen
import com.example.tesis1.screens.MeetingScreen
import com.example.tesis1.screens.RoomScreen
import com.example.tesis1.screens.RoomTopics
import com.example.tesis1.screens.TopicsScreen
import com.example.tesis1.ui.theme.AppTheme
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class MainActivity : ComponentActivity() {

    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startRecording()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private lateinit var apiService: ApiService

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") { LoginScreen(navController) }
                        composable("room") { RoomScreen(navController, topicTitles = listOf("Estrategias Marketing", "Diseño de Interfaces")) }
                        composable("topics") { RoomTopics(navController, roomTitle = "Default Room", topicTitles = listOf("Estrategias Marketing", "Diseño de Interfaces")) }
                        composable("meeting") { MeetingScreen(navController) }
                        composable("history") { HistoryScreen(navController = navController, onItemClick = { item ->
                            navController.navigate("details/$item")
                        }) }
                        composable("history_topics/{roomName}") { backStackEntry ->
                            val roomName = backStackEntry.arguments?.getString("roomName") ?: "Default Room"
                            TopicsScreen(navController, roomName) { navController.popBackStack() }
                        }
                    }
                }
            }
        }

        // Configura Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.56.1:8000/") // Reemplaza con tu URL de backend
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    private fun startRecording() {
        audioFile = File.createTempFile("audio", ".3gp", cacheDir)
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(audioFile?.absolutePath)
            prepare()
            start()
        }


        Handler(Looper.getMainLooper()).postDelayed({
            stopRecording()
            uploadAudio()
        }, 5000)
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }

    private fun uploadAudio() {
        audioFile?.let { file ->
            val requestBody = RequestBody.create("audio/3gp".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("audio", file.name, requestBody)

            apiService.uploadAudio(body).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        // Maneja la respuesta del backend aquí
                        Toast.makeText(this@MainActivity, "Audio uploaded successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Failed to upload audio", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}

