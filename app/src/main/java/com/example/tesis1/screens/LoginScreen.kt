package com.example.tesis1.screens

import ApiService
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.example.tesis1.R
import com.example.tesis1.ui.theme.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    Surface(color = surfaceLight) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ANI",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = surfaceContainerDark,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Assistant Neural Interface",
                style = MaterialTheme.typography.labelSmall,
                color = surfaceContainerDark,
                modifier = Modifier
                    .padding(bottom = 64.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Button(
                onClick = { loginWithAuth0(context, navController) },
                colors = ButtonDefaults.buttonColors(containerColor = inversePrimaryLight),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Login")
            }
        }
    }
}

private fun loginWithAuth0(context: Context, navController: NavController) {
    val clientId = context.getString(R.string.com_auth0_client_id)
    val domain = context.getString(R.string.com_auth0_domain)
    val scheme = context.getString(R.string.com_auth0_scheme)

    val account = Auth0(clientId, domain)
    WebAuthProvider.login(account)
        .withScheme(scheme)
        .withConnection("google-oauth2")
        .start(context, object : Callback<Credentials, AuthenticationException> {
            override fun onSuccess(result: Credentials) {
                val accessToken = result.accessToken
                Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                Log.d("LoginWithAuth0", "Inicio de sesión exitoso. Token de acceso: $accessToken")

                // Llama a sendToken para enviar el token al backend
                sendTokenToBackend(context, accessToken)

                Log.d("LoginWithAuth0", "Navegando a la pantalla de room...")
                navController.navigate("room")
            }

            override fun onFailure(error: AuthenticationException) {
                Toast.makeText(context, "Error de inicio de sesión: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
}

private fun sendTokenToBackend(context: Context, accessToken: String) {
    val apiService = Retrofit.Builder()
        .baseUrl("http://192.168.1.16:8000/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()
        .create(ApiService::class.java)

    val tokenMap = mapOf("token" to accessToken)

    apiService.sendToken(tokenMap).enqueue(object : retrofit2.Callback<Void> {
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            Log.d("sendTokenToBackend", "Token enviado: $accessToken")
            if (response.isSuccessful) {
                Toast.makeText(context, "Token enviado exitosamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error al enviar el token: ${response.message()}", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<Void>, t: Throwable) {
            Toast.makeText(context, "Fallo al enviar el token: ${t.message}", Toast.LENGTH_SHORT).show()
        }
    })
}

