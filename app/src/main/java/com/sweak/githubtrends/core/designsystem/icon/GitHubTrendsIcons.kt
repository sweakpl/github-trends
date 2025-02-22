package com.sweak.githubtrends.core.designsystem.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.sweak.githubtrends.R

object GitHubTrendsIcons {
    val GitHub @Composable get() = ImageVector.vectorResource(R.drawable.ic_github)
    val Fork @Composable get() = ImageVector.vectorResource(R.drawable.ic_fork)

    val Star = Icons.Outlined.Star
    val Growth = Icons.AutoMirrored.Filled.TrendingUp
    val BackArrow = Icons.AutoMirrored.Outlined.ArrowBackIos
    val Error = Icons.Outlined.ErrorOutline
    val LightMode = Icons.Filled.LightMode
    val DarkMode = Icons.Filled.DarkMode
}