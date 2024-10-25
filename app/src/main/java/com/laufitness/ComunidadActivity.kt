package com.laufitness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.laufitness.ui.theme.*
import kotlinx.coroutines.delay

class ComunidadActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LauFitnessTheme {
                val navController = rememberNavController()
                var selectedTab by remember { mutableStateOf("home") }

                Scaffold(
                    bottomBar = {
                        BottomNavigation(
                            backgroundColor = Black,
                            contentColor = White
                        ) {
                            val tabs = listOf(
                                BottomNavItem("Home", Icons.Default.Home, "home"),
                                BottomNavItem("Nutrición", Icons.Default.Favorite, "nutrition"),
                                BottomNavItem("Trainer", Icons.Default.Face, "trainers"),
                                BottomNavItem("Categoría", Icons.Filled.Create, "categoria"),
                                BottomNavItem("Pais", Icons.Filled.LocationOn, "pais")

                            )

                            tabs.forEach { tab ->
                                BottomNavigationItem(
                                    icon = { Icon(imageVector = tab.icon, contentDescription = null) },
                                    label = { Text(tab.label, color = White, fontSize = 12.sp) },
                                    selected = selectedTab == tab.route,
                                    onClick = {
                                        selectedTab = tab.route
                                        navController.navigate(tab.route) {
                                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    selectedContentColor = LimeGreen,
                                    unselectedContentColor = CoolGray
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        Modifier.padding(innerPadding)
                    ) {
                        composable("home") { ComunidadScreen(navController) }
                        composable("nutrition") { NutritionScreen() }
                        composable("trainers") { TrainerScreen() }
                        composable("categoria") { CategoriasScreen() }
                        composable("usuarios") { UsuariosScreen() }
                        composable("pais") { PaisInfoScreen() }

                    }
                }
            }
        }
    }
}

@Composable
fun ComunidadScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Bienvenido, Deiv!",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        val imageList = listOf(
            R.drawable.carousel1,
            R.drawable.carousel2,
            R.drawable.carousel3
        )

        val captions = listOf("Entrena con nosotros", "Alcanza tus metas", "Únete a nuestra comunidad")

        var currentIndex by remember { mutableIntStateOf(0) }

        LaunchedEffect(Unit) {
            while (true) {
                delay(3000)
                currentIndex = (currentIndex + 1) % imageList.size
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(id = imageList[currentIndex]),
                contentDescription = "Carrusel de Comunidad",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = captions[currentIndex],
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(8.dp)
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            ) {
                IconButton(onClick = { /* Acción 1 */ }) {
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Like", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { /* Acción 2 */ }) {
                    Icon(imageVector = Icons.Filled.Info, contentDescription = "Info", tint = Color.White)
                }
            }
        }

        Text(
            text = "Únete a Nuestra Comunidad",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.Info, contentDescription = "Info")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Conéctate con otros entusiastas del fitness y comparte tus experiencias.",
                fontSize = 14.sp
            )
        }

        Text(
            text = "Más de 10,000 miembros activos.",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 2.dp)
        )
        Text(
            text = "Eventos semanales y actividades grupales.",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 2.dp)
        )

        Text(
            text = "Lo más destacado",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            val cardData = listOf(
                Triple(R.drawable.carousel1, "Pro 2024", "Entrenamiento profesional para alcanzar tus objetivos."),
                Triple(R.drawable.one, "Plan Nutricional", "Un plan adaptado a tus necesidades y estilo de vida."),
                Triple(R.drawable.carousel3, "Eventos Especiales", "Participa en eventos y actividades grupales.")
            )

            cardData.forEach { (imageResId, title, description) ->
                Card(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = imageResId),
                            contentDescription = title,
                            modifier = Modifier.size(100.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = description,
                                fontSize = 12.sp,
                                color = CoolGray,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Duración: 1 mes",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "Precio: \$29.99",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row {
                                IconButton(onClick = { /* Acción del botón */ }) {
                                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Like")
                                }
                                IconButton(onClick = { /* Acción del botón */ }) {
                                    Icon(imageVector = Icons.Filled.Info, contentDescription = "Info")
                                }
                            }
                        }
                    }
                }
            }
        }

        // Botón para ver usuarios
        Button(
            onClick = { navController.navigate("usuarios") },
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(50))
        ) {
            Icon(imageVector = Icons.Default.Face, contentDescription = "Ver Usuarios", tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Ver Usuarios", color = Color.White) // Texto blanco
        }
    }
}
