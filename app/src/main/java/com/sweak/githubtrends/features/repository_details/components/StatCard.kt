package com.sweak.githubtrends.features.repository_details.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.designsystem.icon.GitHubTrendsIcons
import com.sweak.githubtrends.core.designsystem.theme.GitHubTrendsTheme
import com.sweak.githubtrends.core.designsystem.theme.space
import com.sweak.githubtrends.core.domain.user.UiThemeMode
import kotlinx.coroutines.delay

@Composable
fun StatCard(
    title: String,
    icon: @Composable () -> Unit,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        ),
        modifier = modifier
            .height(height = MaterialTheme.space.run { xxxLarge + medium })
            .widthIn(min = MaterialTheme.space.run { xxxLarge + medium })
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = MaterialTheme.space.smallMedium)
        ) {
            icon()

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = MaterialTheme.space.xSmall)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .basicMarquee()
            )
        }
    }
}

@Composable
fun AnimatedStatCard(
    title: String,
    icon: @Composable () -> Unit,
    value: String,
    index: Int = 0,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(index * 50L)
        visible = true
    }

    val animatedScale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scaleAnimation"
    )

    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 200),
        label = "alphaAnimation"
    )

    StatCard(
        title = title,
        icon = icon,
        value = value,
        modifier = modifier.graphicsLayer {
            scaleX = animatedScale
            scaleY = animatedScale
            alpha = animatedAlpha
        }
    )
}


@Preview
@Composable
private fun StatCardPreview() {
    GitHubTrendsTheme(uiThemeMode = UiThemeMode.DARK) {
        StatCard(
            title = "Stars",
            icon = {
                Icon(
                    imageVector = GitHubTrendsIcons.Star,
                    contentDescription = stringResource(R.string.content_description_star),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(size = MaterialTheme.space.large)
                )
            },
            value = "181"
        )
    }
}