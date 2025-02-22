package com.sweak.githubtrends.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.sweak.githubtrends.core.domain.user.UiThemeMode

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
    uiThemeMode: UiThemeMode = UiThemeMode.LIGHT,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalSpace provides Space()) {
        val colorScheme = when (uiThemeMode) {
            UiThemeMode.LIGHT -> LightColorScheme
            UiThemeMode.DARK -> DarkColorScheme
        }

        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}