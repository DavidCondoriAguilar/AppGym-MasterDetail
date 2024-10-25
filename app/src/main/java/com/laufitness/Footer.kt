package com.laufitness

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient( // Fondo degradado horizontal
                    colors = listOf(Color(0xFF00C9FF), Color(0xFF92FE9D))
                )
            )
            .shadow(8.dp, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) // Sombra ligera
            .padding(vertical = 12.dp), // Añadir espacio vertical para dar más aire
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FooterItem(icon = Icons.Filled.Email, text = "Contacto")
        FooterDivider() // Separador
        FooterItem(icon = Icons.Filled.Info, text = "Términos")
        FooterDivider() // Separador
        FooterItem(icon = Icons.Filled.Favorite, text = "Instagram")
        FooterDivider() // Separador
        FooterItem(icon = Icons.Filled.PlayArrow, text = "YouTube")
    }
}

@Composable
fun FooterItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 8.dp) // Añadir espacio entre los ítems
    ) {
        // Botón circular para los íconos
        IconButton(
            onClick = { /* Acción */ },
            modifier = Modifier
                .size(48.dp) // Tamaño del botón ícono
                .background(Color.White, CircleShape) // Fondo circular
        ) {
            Icon(icon, contentDescription = text, tint = Color(0xFF1E88E5)) // Color personalizado del ícono
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

@Composable
fun FooterDivider() {
    // Separador vertical para los ítems del footer
    Box(
        modifier = Modifier
            .height(24.dp)
            .width(1.dp)
            .background(Color.White.copy(alpha = 0.5f)) // Divider translúcido
    )
}
