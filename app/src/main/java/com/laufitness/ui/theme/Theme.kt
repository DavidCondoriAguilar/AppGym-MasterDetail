package com.laufitness.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = LimeGreen,    // Verde lima como color primario en modo oscuro
    secondary = CoolGray,   // Gris fresco como color secundario en modo oscuro
    tertiary = DarkGray     // Gris oscuro para detalles en modo oscuro
)

private val LightColorScheme = lightColorScheme(
    primary = DarkGray,     // Gris oscuro como color primario en modo claro
    secondary = SlateGray,  // Gris pizarra como color secundario en modo claro
    tertiary = WhiteGray,   // Gris muy claro para detalles en modo claro
    background = White,     // Blanco como fondo en modo claro
    surface = White,        // Blanco como superficie en modo claro
    onPrimary = White,      // Texto blanco sobre el gris oscuro
    onSecondary = Black,    // Texto negro sobre el gris pizarra
    onBackground = Black,    // Texto negro sobre el fondo blanco
    onSurface = Black       // Texto negro sobre superficies blancas
)

@Composable
fun LauFitnessTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
