package com.sweak.githubtrends.features.repository_details.model

data class RepositoryDetailsWrapper(
    val name: String,
    val username: String,
    val description: String,
    val totalStars: Int,
    val starsSince: Int
)
