package com.sweak.githubtrends.core.domain

import com.sweak.githubtrends.core.domain.util.Error

enum class GitHubError : Error {
    CONNECTION_ERROR,
    SERVER_ERROR,
    UNKNOWN
}