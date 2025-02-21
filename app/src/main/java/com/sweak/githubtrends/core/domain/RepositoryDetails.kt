package com.sweak.githubtrends.core.domain

data class RepositoryDetails(
    val id: String,
    val url: String,
    val name: String,
    val author: String,
    val authorAvatarId: String,
    val description: String?,
    val stars: Int,
    val language: String?,
    val forks: Int
)
