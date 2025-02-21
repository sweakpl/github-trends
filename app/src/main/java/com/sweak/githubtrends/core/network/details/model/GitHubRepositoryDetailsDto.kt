package com.sweak.githubtrends.core.network.details.model

import com.google.gson.annotations.SerializedName

data class GitHubRepositoryDetailsDto(
    val name: String,
    @SerializedName("full_name") val fullName: String,
    val owner: GitHubRepositoryOwnerDto,
    @SerializedName("html_url") val htmlUrl: String,
    val description: String?,
    @SerializedName("stargazers_count") val stargazersCount: Int,
    @SerializedName("forks_count") val forksCount: Int,
    val language: String?
)
