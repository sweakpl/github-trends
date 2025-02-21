package com.sweak.githubtrends.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    background = Bunker,
    onBackground = Color.White,
    surface = Color.Black,
    onSurface = Color.White,
    onSurfaceVariant = Manatee,
    outline = BrightGray
)

private val LightColorScheme = lightColorScheme(
    primary = Bunker,
    background = Color.White,
    onBackground = Bunker,
    surface = AquaHaze,
    onSurfaceVariant = ShuttleGray,
    onSurface = Color.Black,
    outline = Geyser
)

@Composable
fun GitHubTrendsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalSpace provides Space()) {
        val colorScheme = when {
            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}