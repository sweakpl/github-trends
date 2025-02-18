package com.sweak.githubtrends.features.repository_list.model

data class RepositoryPreviewWrapper(
    val name: String,
    val username: String,
    val description: String,
    val totalStars: Int,
    val starsSince: Int
)
