package com.sweak.githubtrends.core.domain.github

data class RepositoryDetails(
    val id: String,
    val url: String,
    val name: String,
    val author: String,
    val authorAvatarUrl: String,
    val description: String?,
    val stars: Int,
    val language: String?,
    val forks: Int
)
