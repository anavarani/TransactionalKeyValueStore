package com.varani.keyvaluestore.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Gray888,
    onPrimary = Color.White,
    secondary = Blue30,
    background = Gray900,
    onSurfaceVariant = GreenGecko,
    tertiary = Color.Black,
    onTertiary = Color.LightGray
)

private val LightColorPalette = lightColorScheme(
    primary = Gray888,
    onPrimary = Gray900,
    secondary = Blue20,
    background = Gray200,
    onSurfaceVariant = Color.Black,
    tertiary = Color.LightGray,
    onTertiary = Color.Black

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun KeyValueStoreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}