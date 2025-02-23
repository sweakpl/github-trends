package com.sweak.githubtrends.features.repository_list.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.designsystem.icon.GitHubTrendsIcons
import com.sweak.githubtrends.core.domain.user.UiThemeMode

@Composable
fun AnimatedThemeIcon(
    uiThemeMode: UiThemeMode,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var rotationState by remember { mutableFloatStateOf(0f) }
    val rotation by animateFloatAsState(
        targetValue = rotationState,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    IconButton(
        onClick = {
            rotationState += 360f
            onClick()
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = when (uiThemeMode) {
                UiThemeMode.LIGHT -> GitHubTrendsIcons.DarkMode
                UiThemeMode.DARK -> GitHubTrendsIcons.LightMode
            },
            contentDescription = stringResource(
                when (uiThemeMode) {
                    UiThemeMode.LIGHT -> R.string.content_description_dark_mode
                    UiThemeMode.DARK -> R.string.content_description_light_mode
                }
            ),
            modifier = Modifier.graphicsLayer {
                rotationZ = rotation
            }
        )
    }
}