package com.laufitness

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class UserDetalleActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Permitir operaciones de red en el hilo principal (para propósitos de demostración)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val userId = intent.getStringExtra("usuarioId") ?: "0"

        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Detalles del Usuario", color = MaterialTheme.colorScheme.onPrimary) },
                        navigationIcon = {
                            IconButton(onClick = { finish() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = MaterialTheme.colorScheme.onPrimary)
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                    )
                }
            ) {
                UserDetailScreen(userId)
            }
        }
    }
}

@Composable
fun UserDetailScreen(userId: String) {
    var usuario by remember { mutableStateOf<Usuarios?>(null) }

    LaunchedEffect(userId) {
        usuario = fetchUsuario(userId)
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Spacer(modifier = Modifier.height(62.dp)) // Espaciado aumentado aquí
        }
        usuario?.let { user ->
            items(listOf(user)) {
                UserDetailCard(user)
            }
        } ?: item {
            Text(text = "Cargando información del usuario...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun UserDetailCard(user: Usuarios) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            UserDetailRow(label = "Nombre:", value = user.nombre, icon = Icons.Filled.Person)
            UserDetailRow(label = "ID:", value = user.id, icon = Icons.Filled.Star)
            UserDetailRow(label = "Correo:", value = user.correo, icon = Icons.Filled.Email)
            UserDetailRow(label = "Fecha de Nacimiento:", value = user.fechaNacimiento, icon = Icons.Filled.DateRange)
            UserDetailRow(label = "Fecha de Registro:", value = user.fechaRegistro, icon = Icons.Filled.DateRange)
            UserDetailRow(label = "ID de Membresía:", value = user.membresiaId.toString(), icon = Icons.Filled.Build)
            UserDetailRow(label = "Membresía:", value = user.membresia, icon = Icons.Filled.Check)
            UserDetailRow(label = "Entrenador:", value = user.entrenador, icon = Icons.Filled.Person)
        }
    }
}

@Composable
fun UserDetailRow(label: String, value: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp), // Espaciado entre filas
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "$label $value", style = MaterialTheme.typography.bodyMedium)
    }
}

fun fetchUsuario(userId: String): Usuarios? {
    val url = URL("http://192.168.1.12/gimnasio/detalle_usuario.php?id=$userId")
    val connection = url.openConnection() as HttpURLConnection

    return try {
        connection.requestMethod = "GET"
        val inputStream = BufferedReader(InputStreamReader(connection.inputStream))
        val response = StringBuilder()
        inputStream.useLines { lines -> lines.forEach { response.append(it) } }

        val jsonObject = JSONObject(response.toString())
        Usuarios(
            id = jsonObject.getString("id"),
            nombre = jsonObject.getString("nombre"),
            correo = jsonObject.getString("correo"),
            fechaNacimiento = jsonObject.getString("fecha_nacimiento"),
            fechaRegistro = jsonObject.getString("fecha_registro"),
            membresiaId = jsonObject.getInt("membresia_id"),
            membresia = jsonObject.getString("membresia"),
            entrenador = jsonObject.getString("entrenador")
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        connection.disconnect()
    }
}
