package com.sweak.githubtrends.core.network.trending

import androidx.annotation.Keep

@Keep
data class TrendingGitHubRepositoryDto(
    val author: String,
    val name: String,
    val description: String?,
    val language: String?,
    val languageColor: String?,
    val stars: Int,
    val currentPeriodStars: Int
)
