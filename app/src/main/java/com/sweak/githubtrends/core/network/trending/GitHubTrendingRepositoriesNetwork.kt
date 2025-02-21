package com.sweak.githubtrends.core.network.trending

import com.sweak.githubtrends.core.domain.util.Result
import com.sweak.githubtrends.core.network.TRENDING_REPOSITORIES_API_URL
import com.sweak.githubtrends.core.network.trending.model.TrendingGitHubRepositoryDto
import com.sweak.githubtrends.core.network.util.GitHubRepositoriesNetworkError
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

private interface GitHubTrendingRepositoriesApi {
    @GET("/repositories")
    suspend fun getTrendingRepositories(): Response<List<TrendingGitHubRepositoryDto>>
}

class GitHubTrendingRepositoriesNetwork @Inject constructor() {
    private val gitHubTrendingRepositoriesApi = Retrofit.Builder()
        .baseUrl(TRENDING_REPOSITORIES_API_URL)
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GitHubTrendingRepositoriesApi::class.java)

    suspend fun getTrendingRepositories(): Result<List<TrendingGitHubRepositoryDto>, GitHubRepositoriesNetworkError> {
        val response: Response<List<TrendingGitHubRepositoryDto>>

        try {
            response = gitHubTrendingRepositoriesApi.getTrendingRepositories()
        } catch (exception: Exception) {
            return when (exception) {
                is ConnectException, is SocketTimeoutException ->
                    Result.Error(GitHubRepositoriesNetworkError.CONNECTION_ERROR)
                is UnknownHostException ->
                    Result.Error(GitHubRepositoriesNetworkError.NO_INTERNET)
                is InterruptedIOException ->
                    Result.Error(GitHubRepositoriesNetworkError.INTERRUPTED)
                else ->
                    Result.Error(GitHubRepositoriesNetworkError.UNKNOWN)
            }
        }

        return if (response.isSuccessful) {
            response.body()?.let {
                Result.Success(it)
            } ?: Result.Error(GitHubRepositoriesNetworkError.UNKNOWN)
        } else {
            when (response.code()) {
                in 400..499 -> Result.Error(GitHubRepositoriesNetworkError.CONNECTION_ERROR)
                in 500..599 -> Result.Error(GitHubRepositoriesNetworkError.SERVER_ERROR)
                else -> Result.Error(GitHubRepositoriesNetworkError.UNKNOWN)
            }
        }
    }
}