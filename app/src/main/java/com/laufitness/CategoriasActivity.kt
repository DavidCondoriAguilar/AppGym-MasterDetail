package com.laufitness

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
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
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CategoriasActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Permitir operaciones de red en el hilo principal (para propósitos de demostración)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Categorías", color = MaterialTheme.colorScheme.onPrimary) },
                        navigationIcon = {
                            IconButton(onClick = { finish() }) {
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
                Column(modifier = Modifier.fillMaxSize().padding(it)) {
                    Spacer(modifier = Modifier.height(26.dp)) // Espacio en la parte superior
                    Header()
                    CategoriasScreen()
                }
            }
        }
    }
}

@Composable
fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Explora Nuestras Categorías",
            style = MaterialTheme.typography.titleLarge,
            color = Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Encuentra la categoría que más te interese.",
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
fun CategoriasScreen() {
    var categorias by remember { mutableStateOf(listOf<Pair<String, String>>()) }

    LaunchedEffect(Unit) {
        categorias = fetchCategorias()
    }

    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(categorias) { (categoria, categoriaId) ->
            CategoryItem(categoria = categoria, categoriaId = categoriaId)
        }
    }
}

@Composable
fun CategoryItem(categoria: String, categoriaId: String) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                val intent = Intent(context, ClasesActivity::class.java).apply {
                    putExtra("categoriaId", categoriaId)
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
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                modifier = Modifier.size(36.dp),
                tint = LimeGreen
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = categoria,
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

// Función para hacer la solicitud HTTP y obtener los datos de categorías
fun fetchCategorias(): List<Pair<String, String>> {
    val url = URL("http://192.168.1.12/gimnasio/categorias.php")
    val connection = url.openConnection() as HttpURLConnection

    return try {
        connection.requestMethod = "GET"
        val inputStream = BufferedReader(InputStreamReader(connection.inputStream))
        val response = StringBuilder()
        inputStream.useLines { lines -> lines.forEach { response.append(it) } }

        // Parsear el JSON
        val jsonArray = JSONArray(response.toString())
        val categoriasList = mutableListOf<Pair<String, String>>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val categoria = jsonObject.getString("NombreDeCategoria")
            val id = jsonObject.getString("ID")
            categoriasList.add(Pair(categoria, id))
        }

        categoriasList
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    } finally {
        connection.disconnect()
    }
}
