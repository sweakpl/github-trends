package com.sweak.githubtrends.features.repository_details.model

data class RepositoryDetailsWrapper(
    val name: String,
    val username: String,
    val usernameAvatarUrl: String,
    val createdAt: Long?,
    val updatedAt: Long?,
    val description: String?,
    val totalStars: Int,
    val language: String?,
    val forks: Int,
    val watchers: Int,
    val openIssues: Int,
    val license: String?,
    val url: String
)
