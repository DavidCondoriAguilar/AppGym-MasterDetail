package com.laufitness

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class InsertarPaisActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var codpais by remember { mutableStateOf("") }
            var pais by remember { mutableStateOf("") }
            var capital by remember { mutableStateOf("") }
            var area by remember { mutableStateOf("") }
            var poblacion by remember { mutableStateOf("") }
            var continente by remember { mutableStateOf("") }

            Column(modifier = Modifier.padding(16.dp)) {
                TextField(value = codpais, onValueChange = { codpais = it }, label = { Text("Código País") })
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = pais, onValueChange = { pais = it }, label = { Text("Nombre del País") })
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = capital, onValueChange = { capital = it }, label = { Text("Capital") })
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = area, onValueChange = { area = it }, label = { Text("Área (km²)") })
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = poblacion, onValueChange = { poblacion = it }, label = { Text("Población") })
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = continente, onValueChange = { continente = it }, label = { Text("Continente") })
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    insertarPais(codpais, pais, capital, area, poblacion, continente)
                }) {
                    Text("Insertar País")
                }
            }
        }
    }

    private fun insertarPais(codpais: String, pais: String, capital: String, area: String, poblacion: String, continente: String) {
        val url = URL("https://servicios.campus.pe/paisesinsert.php")

        Thread {
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true

            val params = "codpais=$codpais&pais=$pais&capital=$capital&area=$area&poblacion=$poblacion&continente=$continente"
            val outputStream: OutputStreamWriter = OutputStreamWriter(connection.outputStream)
            outputStream.write(params)
            outputStream.flush()
            outputStream.close()

            val responseCode = connection.responseCode
            runOnUiThread {
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Toast.makeText(this, "País insertado correctamente", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK) // Indica que la inserción fue exitosa
                    finish() // Cierra la actividad
                } else {
                    Toast.makeText(this, "Error al insertar el país", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }
}
