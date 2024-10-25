package com.laufitness

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.laufitness.ui.theme.LauFitnessTheme

class PaisInfo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LauFitnessTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PaisInfoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PaisInfoScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Información sobre los Países",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Información adicional
        Row(modifier = Modifier.padding(bottom = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Filled.Info, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Aquí puedes agregar, buscar y ver información sobre diferentes países.")
        }

        // Lista de países locales
        val paises = listOf("Argentina", "Brasil", "Chile", "Colombia", "México", "Perú", "Uruguay")
        paises.forEach { pais ->
            Text(text = pais, modifier = Modifier.padding(4.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Navegar a PaisActivity
            val intent = Intent(context, PaisActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("Ver Países")
        }
    }
}

