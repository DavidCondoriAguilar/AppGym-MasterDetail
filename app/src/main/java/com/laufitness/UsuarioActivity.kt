package com.laufitness

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.laufitness.ui.theme.Black
import com.laufitness.ui.theme.LimeGreen
import com.laufitness.ui.theme.WhiteGray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class UsuarioActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Usuarios", color = MaterialTheme.colorScheme.onPrimary) },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                    )
                }
            ) {
                Column(modifier = Modifier.fillMaxSize().padding(it)) {
                    Spacer(modifier = Modifier.height(26.dp)) // Espacio en la parte superior
                    Head()
                    UsuariosScreen()

                    // Espaciado para empujar el botón hacia abajo
                    Spacer(modifier = Modifier.weight(1f))

                    // Botón para regresar
                    Button(
                        onClick = { finish() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp) // Margen del botón
                    ) {
                        Text("Regresar")
                    }
                }
            }
        }
    }
}

@Composable
fun Head() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Explora Nuestros Usuarios",
            style = MaterialTheme.typography.titleLarge,
            color = Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Encuentra la información de los usuarios.",
            style = MaterialTheme.typography.bodyMedium,
            color = Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Icon(
            imageVector = Icons.Filled.Info,
            contentDescription = "Información",
            tint = LimeGreen,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
fun UsuariosScreen() {
    var usuarios by remember { mutableStateOf(listOf<Usuario>()) }

    LaunchedEffect(Unit) {
        // Llamada a la función en un hilo de fondo
        usuarios = withContext(Dispatchers.IO) {
            fetchUsuarios()
        }
    }

    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(usuarios) { usuario ->
            UsuarioItem(usuario = usuario)
        }
    }
}

@Composable
fun UsuarioItem(usuario: Usuario) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                val intent = Intent(context, UserDetalleActivity::class.java).apply {
                    putExtra("usuarioId", usuario.id)
                }
                context.startActivity(intent)
            },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = WhiteGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                modifier = Modifier.size(36.dp),
                tint = LimeGreen
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = usuario.nombre,
                style = MaterialTheme.typography.bodyLarge,
                color = Black
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = LimeGreen
            )
        }
    }
}

data class Usuario(val id: String, val nombre: String, val correo: String)

fun fetchUsuarios(): List<Usuario> {
    val url = URL("http://192.168.1.12/gimnasio/usuarios.php") // Cambia la dirección IP si es necesario
    val connection = url.openConnection() as HttpURLConnection

    return try {
        connection.requestMethod = "GET"
        val inputStream = BufferedReader(InputStreamReader(connection.inputStream))
        val response = StringBuilder()
        inputStream.useLines { lines -> lines.forEach { response.append(it) } }

        // Parsear el JSON
        val jsonArray = JSONArray(response.toString())
        val usuariosList = mutableListOf<Usuario>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getString("id")
            val nombre = jsonObject.getString("nombre")
            val correo = jsonObject.getString("correo")
            usuariosList.add(Usuario(id, nombre, correo))
        }

        usuariosList
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    } finally {
        connection.disconnect()
    }
}
