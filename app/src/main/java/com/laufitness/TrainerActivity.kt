package com.laufitness

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.laufitness.ui.theme.*

class TrainerActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LauFitnessTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(
                        title = { Text(text = "Entrenadores") },
                        navigationIcon = {
                            IconButton(onClick = { finish() }) { // Termina la actividad actual
                                Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
                            }
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = White)
                    )
                }) { innerPadding ->
                    TrainerScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TrainerScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current // Obtén el contexto local
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White) // Fondo blanco
            .padding(16.dp)
    ) {
        Text(
            text = "Nuestros Entrenadores",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Black
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Listado de entrenadores
        TrainerList()

        // Detalles adicionales
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Más sobre Nuestros Entrenadores",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Black,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Text(
            text = "Nuestros entrenadores están formados en las mejores prácticas para tu bienestar.",
            fontSize = 16.sp,
            color = CoolGray
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¡Únete a nosotros y transforma tu vida con la ayuda de nuestros expertos!",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = LimeGreen
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Texto informativo sobre categorías
        Text(
            text = "Haz clic en el botón para conocer más sobre nuestras categorías de clases y encontrar la que mejor se adapte a ti.",
            fontSize = 14.sp,
            color = CoolGray
        )

        // Botón de acción
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                val intent = Intent(context, CategoriasActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = LimeGreen)
        ) {
            Text(
                text = "Conoce nuestras categorías",
                color = White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TrainerList() {
    val trainerImages = listOf(
        R.drawable.one,
        R.drawable.trainer11,
        R.drawable.trainer2
    )

    val trainerNames = listOf("Juan Pérez", "María Gómez", "Carlos Ruiz")
    val specialties = listOf("Entrenador Personal", "Nutricionista", "Instructora de Yoga")

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        for (index in trainerImages.indices) {
            TrainerItem(
                image = trainerImages[index],
                name = trainerNames[index],
                specialty = specialties[index],
                description = "Descripción de ${trainerNames[index]} aquí."
            )
        }
    }
}

@Composable
fun TrainerItem(image: Int, name: String, specialty: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(vertical = 8.dp), // Espaciado vertical
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Imagen circular del entrenador
            Image(
                painter = painterResource(id = image),
                contentDescription = "Imagen de $name",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Información del entrenador
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Black
            )
            Text(
                text = specialty,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = CoolGray
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Descripción del entrenador
            Text(
                text = description,
                fontSize = 12.sp,
                color = CoolGray
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Iconos con detalles adicionales
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(Icons.Filled.Person, contentDescription = "Icono Nombre", tint = LimeGreen)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "5 años de experiencia", fontSize = 12.sp, color = CoolGray)

                Spacer(modifier = Modifier.width(16.dp))

                Icon(Icons.Filled.ThumbUp, contentDescription = "Icono Fitness", tint = LimeGreen)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Especializado en Fitness", fontSize = 12.sp, color = CoolGray)
            }
        }
    }
}
