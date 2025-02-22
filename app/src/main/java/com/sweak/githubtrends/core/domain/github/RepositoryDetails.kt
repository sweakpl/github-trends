package com.sweak.githubtrends.core.domain.github

data class RepositoryDetails(
    val id: String,
    val url: String,
    val name: String,
    val author: String,
    val authorAvatarUrl: String,
    val createdAt: Long?,
    val updatedAt: Long?,
    val description: String?,
    val stars: Int,
    val language: String?,
    val forks: Int,
    val watchers: Int,
    val openIssues: Int,
    val license: String?
)
