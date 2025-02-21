package com.sweak.githubtrends.core.data

import com.sweak.githubtrends.core.domain.GitHubError
import com.sweak.githubtrends.core.domain.GitHubRepository
import com.sweak.githubtrends.core.domain.RepositoryDetails
import com.sweak.githubtrends.core.domain.TrendingRepository
import com.sweak.githubtrends.core.domain.util.Result
import com.sweak.githubtrends.core.network.details.GitHubRepositoryDetailsNetwork
import com.sweak.githubtrends.core.network.trending.GitHubTrendingRepositoriesNetwork
import com.sweak.githubtrends.core.network.util.GitHubRepositoriesNetworkError
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val gitHubTrendingRepositoriesNetwork: GitHubTrendingRepositoriesNetwork,
    private val gitHubRepositoryDetailsNetwork: GitHubRepositoryDetailsNetwork
) : GitHubRepository {

    override suspend fun getTrendingRepositories(): Result<List<TrendingRepository>, GitHubError> {
        when (val result = gitHubTrendingRepositoriesNetwork.getTrendingRepositories()) {
            is Result.Error -> {
                return when (result.error) {
                    GitHubRepositoriesNetworkError.NO_INTERNET,
                    GitHubRepositoriesNetworkError.CONNECTION_ERROR ->
                        Result.Error(GitHubError.CONNECTION_ERROR)
                    GitHubRepositoriesNetworkError.SERVER_ERROR ->
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

    override suspend fun getRepositoryDetails(
        repositoryId: String
    ): Result<RepositoryDetails, GitHubError> {
        val result = gitHubRepositoryDetailsNetwork.getRepositoryDetails(
            repositoryId = repositoryId
        )

        when (result) {
            is Result.Error -> {
                return when (result.error) {
                    GitHubRepositoriesNetworkError.NO_INTERNET,
                    GitHubRepositoriesNetworkError.CONNECTION_ERROR ->
                        Result.Error(GitHubError.CONNECTION_ERROR)
                    GitHubRepositoriesNetworkError.SERVER_ERROR ->
                        Result.Error(GitHubError.SERVER_ERROR)
                    else -> Result.Error(GitHubError.UNKNOWN)
                }
            }
            is Result.Success -> {
                return Result.Success(
                    RepositoryDetails(
                        id = result.data.fullName,
                        url = result.data.htmlUrl,
                        name = result.data.name,
                        author = result.data.owner.login,
                        authorAvatarId = result.data.owner.avatarUrl,
                        description = result.data.description,
                        stars = result.data.stargazersCount,
                        language = result.data.language,
                        forks = result.data.forksCount,
                    )
                )
            }
        }
    }
}