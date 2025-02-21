package com.sweak.githubtrends.features.repository_list.model

data class RepositoryPreviewWrapper(
    val id: String,
    val name: String,
    val username: String,
    val description: String?,
    val language: String?,
    val totalStars: Int,
    val starsSince: Int
)
