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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.laufitness.ui.theme.*

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LauFitnessTheme {
                RegisterScreen(this)
            }
        }
    }
}

@Composable
fun RegisterScreen(activity: RegisterActivity) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
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
            // Ícono de retroceso
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Volver atrás",
                    modifier = Modifier
                        .clickable { activity.finish() } // Cierra la actividad actual
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
                Text(
                    text = "Volver",
                    style = AppTypography.bodyMedium.copy(color = LimeGreen),
                    modifier = Modifier.clickable { activity.finish() }
                )
            }

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
                text = "Crea tu cuenta en DeivFit",
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

            // Campo de confirmación de contraseña
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar contraseña") },
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
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Botón de registro
            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                        errorMessage = "Por favor completa todos los campos."
                    } else if (password != confirmPassword) {
                        errorMessage = "Las contraseñas no coinciden."
                    } else {
                        // Aquí podrías añadir la lógica para registrar al usuario.
                        errorMessage = ""
                        // Para la demostración, vamos a redirigir a la pantalla de inicio de sesión
                        val intent = Intent(activity, LoginActivity::class.java)
                        activity.startActivity(intent)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = LimeGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = "Registrarse",
                    style = AppTypography.titleMedium.copy(color = Color.White)
                )
            }
        }
    }
}
