package com.sweak.githubtrends.core.domain

import com.sweak.githubtrends.core.domain.util.Result

interface GitHubRepository {
    suspend fun getTrendingRepositories(): Result<List<TrendingRepository>, GitHubError>
    suspend fun getRepositoryDetails(repositoryId: String): Result<RepositoryDetails, GitHubError>
}