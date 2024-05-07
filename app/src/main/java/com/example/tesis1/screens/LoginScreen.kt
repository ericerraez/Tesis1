package com.example.tesis1.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tesis1.ui.theme.*
import com.auth0.android.Auth0
import com.auth0.android.provider.WebAuthProvider
import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import com.example.tesis1.R

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
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Assistant Neural Interface",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .padding(bottom = 64.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { loginWithAuth0(context, navController) },
                    colors = ButtonDefaults.buttonColors(containerColor = inversePrimaryLight)
                ) {
                    Text("Login")
                }

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Sign Up")
                }
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
        .start(context, object : Callback<Credentials, AuthenticationException> {
            override fun onSuccess(result: Credentials) {
                val accessToken = result.accessToken
                Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                navController.navigate("room")
            }

            override fun onFailure(error: AuthenticationException) {
                Toast.makeText(context, "Error de inicio de sesión: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
}