package com.laufitness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.laufitness.ui.theme.*

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LauFitnessTheme {
                val navController = rememberNavController()
                var selectedTab by remember { mutableStateOf("home") }

                Scaffold(
                    bottomBar = {
                        BottomNavigation(
                            backgroundColor = Black,  // Usar negro como color de fondo
                            contentColor = White // Color de contenido blanco
                        ) {
                            val tabs = listOf(
                                BottomNavItem("Comunidad", Icons.Default.Home, "home"),
                                BottomNavItem("Nutrición", Icons.Default.Favorite, "nutrition"),
                                BottomNavItem("Entrenadores", Icons.Default.Face, "trainers"),
                                BottomNavItem("Categorías", Icons.Filled.Create, "categories"),
                                BottomNavItem("Room", Icons.Filled.Create, "room") // Nueva opción "Room"
                            )

                            tabs.forEach { tab ->
                                BottomNavigationItem(
                                    icon = { Icon(imageVector = tab.icon, contentDescription = null) },
                                    label = { Text(tab.label, color = White) }, // Color del texto a blanco
                                    selected = selectedTab == tab.route,
                                    onClick = {
                                        selectedTab = tab.route
                                        navController.navigate(tab.route) {
                                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    selectedContentColor = LimeGreen, // Color verde lima brillante cuando está seleccionado
                                    unselectedContentColor = CoolGray // Color gris fresco cuando no está seleccionado
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
                        composable("trainers") { WelcomeScreen() }
                        composable("categories") { CategoriasScreen() }
                        composable("room") { WelcomeScreen() } // Navegación a la pantalla de Room
                    }
                }
            }
        }
    }
}

data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)
