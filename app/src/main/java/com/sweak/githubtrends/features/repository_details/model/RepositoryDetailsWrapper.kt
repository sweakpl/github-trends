package com.sweak.githubtrends.features.repository_details.model

data class RepositoryDetailsWrapper(
    val name: String,
    val username: String,
    val usernameAvatarUrl: String,
    val description: String?,
    val totalStars: Int,
    val language: String?,
    val forks: Int,
    val url: String
)
