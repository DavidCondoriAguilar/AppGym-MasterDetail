package com.laufitness

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.sharp.PlayArrow
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.unit.sp
import com.laufitness.ui.theme.*

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LauFitnessTheme {
                LoginScreen(this)
            }
        }
    }
}

@Composable
fun LoginScreen(activity: LoginActivity) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "LauFitness Logo",
                modifier = Modifier
                    .size(250.dp)
                    .padding(bottom = 32.dp)
            )

            // Texto de bienvenida
            Text(
                text = "Bienvenido a DeivFit",
                style = AppTypography.headlineLarge.copy(fontSize = 28.sp),
                color = LimeGreen,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Campo de correo electrónico
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Icono de correo electrónico"
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Campo de contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Icono de candado"
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Mensaje de error
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Button(
                onClick = {
                    if (email == "" && password == "") {
                        errorMessage = ""
                        val intent = Intent(activity, WelcomeActivity::class.java).apply {
                            putExtra("USER_NAME", "Deiv") // Puedes personalizar el nombre de usuario
                        }
                        activity.startActivity(intent)
                    } else {
                        errorMessage = "Credenciales incorrectas. Inténtalo nuevamente."
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = LimeGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Sharp.PlayArrow,
                    contentDescription = "Iniciar sesión",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Iniciar sesión",
                    style = AppTypography.titleMedium.copy(color = Color.White)
                )
            }

            // Texto de opción de registro
            Text(
                text = "¿No tienes una cuenta? Regístrate",
                style = AppTypography.bodySmall.copy(color = LimeGreen),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable {
                        val intent = Intent(activity, RegisterActivity::class.java)
                        activity.startActivity(intent)
                    }
            )
        }
    }
}
