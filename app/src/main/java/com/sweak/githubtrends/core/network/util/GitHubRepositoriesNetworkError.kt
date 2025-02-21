package com.sweak.githubtrends.core.network.util

import com.sweak.githubtrends.core.domain.util.Error

enum class GitHubRepositoriesNetworkError : Error {
    NO_INTERNET,
    CONNECTION_ERROR,
    INTERRUPTED,
    SERVER_ERROR,
    UNKNOWN
}