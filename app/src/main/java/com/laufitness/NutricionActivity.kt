package com.laufitness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.laufitness.ui.theme.LauFitnessTheme
import com.laufitness.ui.theme.LimeGreen
import com.laufitness.ui.theme.DarkGray
import kotlinx.coroutines.delay
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

class NutricionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LauFitnessTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NutritionScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun NutritionScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Nutrición",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = DarkGray
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Carrusel de imágenes de alimentos saludables
        val imageList = listOf(
            R.drawable.saludable2,
            R.drawable.saludable,
            R.drawable.water
        )
        val captions = listOf("Alimentos Frescos", "Dieta Balanceada", "Hidratación")

        var currentIndex by remember { mutableStateOf(0) }

        LaunchedEffect(Unit) {
            while (true) {
                delay(3000) // Cambiar cada 3 segundos
                currentIndex = (currentIndex + 1) % imageList.size
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
        ) {
            Image(
                painter = painterResource(id = imageList[currentIndex]),
                contentDescription = "Carrusel de Nutrición",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(8.dp)
            ) {
                Text(
                    text = captions[currentIndex],
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Icon(Icons.Filled.Favorite, contentDescription = null, tint = LimeGreen, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Filled.Info, contentDescription = null, tint = LimeGreen, modifier = Modifier.size(24.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Texto descriptivo
        Text(
            text = "Consejos de Nutrición",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Tarjetas informativas en dos columnas
        val buttons = listOf(
            Pair(Icons.Filled.DateRange, "Ver Recetas"),
            Pair(Icons.Filled.Favorite, "Favoritos"),
            Pair(Icons.Filled.Add, "Añadir Comida"),
            Pair(Icons.Filled.Info, "Info")
        )

        Column {
            for (i in buttons.indices step 2) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (j in 0..1) {
                        val index = i + j
                        if (index < buttons.size) {
                            Column(modifier = Modifier.weight(1f)) {
                                Card(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp),
                                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = buttons[index].first,
                                            contentDescription = buttons[index].second,
                                            modifier = Modifier.size(40.dp),
                                            tint = LimeGreen
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(
                                            text = buttons[index].second,
                                            fontSize = 16.sp,
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Información adicional sobre nutrición
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Información Adicional",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Text(
            text = "Una dieta equilibrada incluye:",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        val foodGroups = listOf(
            "1. Frutas y Verduras: Ricas en vitaminas y minerales.",
            "2. Proteínas: Ayudan en la reparación de tejidos.",
            "3. Granos Enteros: Fuente de energía y fibra.",
            "4. Lácteos: Fuente de calcio y vitamina D.",
            "5. Grasas Saludables: Importantes para el corazón."
        )

        foodGroups.forEach { foodGroup ->
            Text(
                text = foodGroup,
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }

        // Sección de recetas destacadas
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Recetas Destacadas",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Listado de recetas
        val recipes = listOf(
            Pair(R.drawable.qinu, "Ensalada de Quinoa"),
            Pair(R.drawable.smoti, "Smoothie Verde"),
            Pair(R.drawable.acai, "Tazón de Acai")
        )

        recipes.forEach { recipe ->
            RecipeCard(recipe = recipe.first, recipeName = recipe.second)
        }
    }
}

@Composable
fun RecipeCard(recipe: Int, recipeName: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = recipe),
                contentDescription = recipeName,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = recipeName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
