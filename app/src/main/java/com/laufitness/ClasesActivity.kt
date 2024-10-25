package com.laufitness

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ClasesActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Permitir operaciones de red en el hilo principal (para propósitos de demostración)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val categoriaID = intent.getStringExtra("categoriaId") ?: ""

        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Clases de Categoría: $categoriaID", color = MaterialTheme.colorScheme.onPrimary) },
                        navigationIcon = {
                            IconButton(onClick = {
                                // Acción al regresar a la pantalla de categorías
                                finish() // Cierra esta actividad y vuelve a la anterior
                            }) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Regresar",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                    )
                }
            ) {
                ClasesScreen(categoriaID)
            }
        }
    }
}

@Composable
fun ClasesScreen(categoriaID: String) {
    var clases by remember { mutableStateOf(listOf<Clase>()) }

    LaunchedEffect(categoriaID) {
        clases = fetchClases(categoriaID)
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(clases) { clase ->
            ClaseItem(clase)
        }
    }
}

@Composable
fun ClaseItem(clase: Clase) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface) // Fondo de la tarjeta
    ) {
        Text(
            text = "Nombre: ${clase.nombreDeClase}",
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface)
        )
        Text(
            text = "Instructor: ${clase.instructor}",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
        )
        Text(
            text = "Horario: ${clase.horario}",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
        )
        Text(
            text = "Duración: ${clase.duracion}",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
        )
        Text(
            text = "Nivel de dificultad: ${clase.nivelDeDificultad}",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
        )
        Spacer(modifier = Modifier.height(8.dp)) // Espacio entre items
        HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)) // Separador entre las clases
    }
}

fun fetchClases(categoriaID: String): List<Clase> {
    val url = URL("http://192.168.1.12/gimnasio/clases.php") // FETCH MEDIANTE IP
    val connection = url.openConnection() as HttpURLConnection

    return try {
        connection.requestMethod = "GET"
        val inputStream = BufferedReader(InputStreamReader(connection.inputStream))
        val response = StringBuilder()
        inputStream.useLines { lines -> lines.forEach { response.append(it) } }

        // Parsear el JSON
        val jsonArray = JSONArray(response.toString())
        val clasesList = mutableListOf<Clase>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getString("ID")
            val nombreDeClase = jsonObject.getString("NombreDeClase")
            val instructor = jsonObject.getString("Instructor")
            val horario = jsonObject.getString("Horario")
            val duracion = jsonObject.getString("Duracion")
            val nivelDeDificultad = jsonObject.getString("NivelDeDificultad")
            val categoria = jsonObject.getString("CategoriaID")

            // Filtrar clases por ID de categoría
            if (categoria == categoriaID) {
                clasesList.add(Clase(id, nombreDeClase, instructor, horario, duracion, nivelDeDificultad, categoria))
            }
        }

        clasesList
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    } finally {
        connection.disconnect()
    }
}
