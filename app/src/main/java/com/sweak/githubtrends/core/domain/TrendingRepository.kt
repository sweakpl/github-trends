package com.sweak.githubtrends.core.domain

data class TrendingRepository(
    val id: String,
    val name: String,
    val author: String,
    val description: String?,
    val language: String?,
    val stars: Int,
    val starsSince: Int
)
