package com.sweak.githubtrends.core.network.trending

import com.sweak.githubtrends.core.domain.util.Error

enum class GitHubTrendingRepositoriesNetworkError : Error {
    NO_INTERNET,
    CONNECTION_ERROR,
    INTERRUPTED,
    SERVER_ERROR,
    UNKNOWN
}