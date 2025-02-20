package com.sweak.githubtrends.core.data

import com.sweak.githubtrends.core.domain.GitHubError
import com.sweak.githubtrends.core.domain.GitHubRepository
import com.sweak.githubtrends.core.domain.TrendingRepository
import com.sweak.githubtrends.core.domain.util.Result
import com.sweak.githubtrends.core.network.trending.GitHubTrendingRepositoriesNetwork
import com.sweak.githubtrends.core.network.trending.GitHubTrendingRepositoriesNetworkError
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val gitHubTrendingRepositoriesNetwork: GitHubTrendingRepositoriesNetwork
) : GitHubRepository {

    override suspend fun getTrendingRepositories(): Result<List<TrendingRepository>, GitHubError> {
        when (val result = gitHubTrendingRepositoriesNetwork.getTrendingRepositories()) {
            is Result.Error -> {
                return when (result.error) {
                    GitHubTrendingRepositoriesNetworkError.NO_INTERNET,
                    GitHubTrendingRepositoriesNetworkError.CONNECTION_ERROR ->
                        Result.Error(GitHubError.CONNECTION_ERROR)
                    GitHubTrendingRepositoriesNetworkError.SERVER_ERROR ->
                        Result.Error(GitHubError.SERVER_ERROR)
                    else -> Result.Error(GitHubError.UNKNOWN)
                }
            }
            is Result.Success -> {
                return Result.Success(
                    result.data.map { trendingGitHubRepositoryDto ->
                        TrendingRepository(
                            id = "${trendingGitHubRepositoryDto.author}/${trendingGitHubRepositoryDto.name}",
                            name = trendingGitHubRepositoryDto.name,
                            author = trendingGitHubRepositoryDto.author,
                            description = trendingGitHubRepositoryDto.description,
                            stars = trendingGitHubRepositoryDto.stars,
                            starsSince = trendingGitHubRepositoryDto.currentPeriodStars
                        )
                    }
                )
            }
        }
    }
}