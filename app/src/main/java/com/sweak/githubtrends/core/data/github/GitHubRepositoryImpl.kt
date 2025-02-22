package com.sweak.githubtrends.core.data.github

import com.sweak.githubtrends.core.domain.github.GitHubError
import com.sweak.githubtrends.core.domain.github.GitHubRepository
import com.sweak.githubtrends.core.domain.github.RepositoryDetails
import com.sweak.githubtrends.core.domain.github.TrendingRepository
import com.sweak.githubtrends.core.domain.util.Result
import com.sweak.githubtrends.core.network.details.GitHubRepositoryDetailsNetwork
import com.sweak.githubtrends.core.network.trending.GitHubTrendingRepositoriesNetwork
import com.sweak.githubtrends.core.network.util.GitHubRepositoriesNetworkError
import java.time.Instant
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
                            language = trendingGitHubRepositoryDto.language,
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
        val ownerName: String
        val repositoryName: String

        try {
            repositoryId.split("/").let {
                require(it.size == 2)

                ownerName = it[0]
                repositoryName = it[1]
            }
        } catch (illegalArgumentException: IllegalArgumentException) {
            return Result.Error(GitHubError.UNKNOWN)
        }

        val result = gitHubRepositoryDetailsNetwork.getRepositoryDetails(
            repositoryOwnerName = ownerName,
            repositoryName = repositoryName
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
                        authorAvatarUrl = result.data.owner.avatarUrl,
                        createdAt = result.data.createdAt?.let {
                            Instant.parse(it).toEpochMilli()
                        },
                        updatedAt = result.data.updatedAt?.let {
                            Instant.parse(it).toEpochMilli()
                        },
                        description = result.data.description,
                        stars = result.data.stargazersCount,
                        language = result.data.language,
                        forks = result.data.forksCount,
                        watchers = result.data.subscribersCount,
                        openIssues = result.data.openIssues,
                        license = result.data.license?.spdxId
                    )
                )
            }
        }
    }
}