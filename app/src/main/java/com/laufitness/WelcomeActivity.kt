package com.laufitness

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.laufitness.ui.theme.*

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LauFitnessTheme {
                WelcomeScreen()
            }
        }
    }
}

@Composable
fun WelcomeScreen() {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo de la imagen
        Image(
            painter = painterResource(id = R.drawable.one), // Cambia a tu imagen de fondo
            contentDescription = "Fondo de Bienvenida",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Superposición de fondo oscuro
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        // Contenido de la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Dondequiera que estés",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "La Salud es lo Primero",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = LimeGreen
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No hay un camino instantáneo hacia una vida saludable.",
                fontSize = 18.sp,
                color = CoolGray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LimeGreen),
                shape = MaterialTheme.shapes.medium
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Iniciar",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Comenzar",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = "Advertencia",
                    tint = LimeGreen,
                    modifier = Modifier.size(36.dp)
                )
            },
            title = {
                Text(
                    text = "¿Estás seguro?",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGray
                )
            },
            text = {
                Text(
                    text = "¿Seguro que quieres continuar? Esta decisión podría cambiar tu vida.",
                    fontSize = 16.sp,
                    color = CoolGray
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        val intent = Intent(context, ComunidadActivity::class.java)
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = LimeGreen)
                ) {
                    Text(text = "Sí", color = White)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = LimeGreen)
                ) {
                    Text(text = "No")
                }
            },
            containerColor = WhiteGray,
            tonalElevation = 8.dp
        )
    }
}
