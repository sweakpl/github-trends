package com.sweak.githubtrends.core.designsystem.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.sweak.githubtrends.R

object GitHubTrendsIcons {
    val GitHub @Composable get() = ImageVector.vectorResource(R.drawable.ic_github)
    val Fork @Composable get() = ImageVector.vectorResource(R.drawable.ic_fork)
    val Star @Composable get() = ImageVector.vectorResource(R.drawable.ic_star)
    val Growth @Composable get() = ImageVector.vectorResource(R.drawable.ic_trend)
    val Issue @Composable get() = ImageVector.vectorResource(R.drawable.ic_issue)
    val License @Composable get() = ImageVector.vectorResource(R.drawable.ic_license)
    val Watch @Composable get() = ImageVector.vectorResource(R.drawable.ic_watch)

    val BackArrow = Icons.AutoMirrored.Outlined.ArrowBackIos
    val Error = Icons.Outlined.ErrorOutline
    val LightMode = Icons.Filled.LightMode
    val DarkMode = Icons.Filled.DarkMode
}