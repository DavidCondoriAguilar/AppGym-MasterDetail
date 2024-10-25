package com.laufitness

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.laufitness.ui.theme.Black
import com.laufitness.ui.theme.LauFitnessTheme
import com.laufitness.ui.theme.LimeGreen
import com.laufitness.ui.theme.WhiteGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class PaisActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LauFitnessTheme {
                val navController = rememberNavController()
                MainScreen(navController)
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val paisesState = remember { mutableStateOf(emptyList<Pais>()) }
    val context = LocalContext.current

    val activityResultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Actualiza la lista de países
            fetchPaises(paisesState)
        }
    }

    LaunchedEffect(Unit) {
        fetchPaises(paisesState) // Cargar países al iniciar
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                val intent = Intent(context, InsertarPaisActivity::class.java)
                activityResultLauncher.launch(intent)
            },
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            Text("Agregar Nuevo País")
        }

        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar País") },
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        )

        Locaso()
        PaisesScreen(navController, searchQuery, paisesState)
    }
}

@Composable
fun Locaso() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Explora Nuestros Países",
            style = MaterialTheme.typography.titleLarge,
            color = Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Encuentra información sobre los países.",
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
fun PaisesScreen(navController: NavController, searchQuery: String, paisesState: MutableState<List<Pais>>) {
    val paises by paisesState

    // Filtrar los países según el texto de búsqueda
    val filteredPaises = if (searchQuery.isEmpty()) {
        paises
    } else {
        paises.filter { it.pais.contains(searchQuery, ignoreCase = true) }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(filteredPaises) { pais ->
            PaisItem(pais = pais, navController)
        }
    }
}

@Composable
fun PaisItem(pais: Pais, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // Navegar a la actividad de detalles del país (implementa según sea necesario)
            },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = WhiteGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "País: ${pais.pais} (${pais.codpais})",
                style = MaterialTheme.typography.bodyLarge,
                color = Black
            )
            Text(
                text = "Capital: ${pais.capital}",
                style = MaterialTheme.typography.bodyMedium,
                color = Black
            )
            Text(
                text = "Área: ${pais.area} km²",
                style = MaterialTheme.typography.bodyMedium,
                color = Black
            )
            Text(
                text = "Población: ${pais.poblacion}",
                style = MaterialTheme.typography.bodyMedium,
                color = Black
            )
            Text(
                text = "Continente: ${pais.continente}",
                style = MaterialTheme.typography.bodyMedium,
                color = Black
            )
        }
    }
}

fun fetchPaises(paisesState: MutableState<List<Pais>>) {
    CoroutineScope(Dispatchers.IO).launch {
        val url = URL("https://servicios.campus.pe/paises.php")
        val connection = url.openConnection() as HttpURLConnection

        val paisesList = try {
            connection.requestMethod = "GET"
            val inputStream = BufferedReader(InputStreamReader(connection.inputStream))
            val response = StringBuilder()
            inputStream.useLines { lines -> lines.forEach { response.append(it) } }

            val jsonArray = JSONArray(response.toString())
            val paises = mutableListOf<Pais>()

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val idpais = jsonObject.getString("idpais")
                val codpais = jsonObject.getString("codpais")
                val pais = jsonObject.getString("pais")
                val capital = jsonObject.getString("capital")
                val area = jsonObject.getString("area")
                val poblacion = jsonObject.getString("poblacion")
                val continente = jsonObject.getString("continente")
                paises.add(Pais(idpais, codpais, pais, capital, area, poblacion, continente))
            }

            paises
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            connection.disconnect()
        }

        // Actualiza el estado en el hilo principal
        withContext(Dispatchers.Main) {
            paisesState.value = paisesList
        }
    }
}
