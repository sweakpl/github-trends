package com.sweak.githubtrends.core.network.details.model

import com.google.gson.annotations.SerializedName

data class GitHubRepositoryOwnerDto(
    val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
)
