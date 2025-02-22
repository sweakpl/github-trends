package com.sweak.githubtrends.core.network.details

import com.sweak.githubtrends.core.domain.util.Result
import com.sweak.githubtrends.core.network.GITHUB_API_URL
import com.sweak.githubtrends.core.network.details.model.GitHubRepositoryDetailsDto
import com.sweak.githubtrends.core.network.util.GitHubRepositoriesNetworkError
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

private interface GitHubRepositoryDetailsApi {
    @GET("/repos/{owner}/{repo}")
    suspend fun getRepositoryDetails(
        @Path("owner") ownerName: String,
        @Path("repo") repositoryName: String,
    ): Response<GitHubRepositoryDetailsDto>
}

class GitHubRepositoryDetailsNetwork @Inject constructor() {
    private val gitHubRepositoryDetailsApi = Retrofit.Builder()
        .baseUrl(GITHUB_API_URL)
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GitHubRepositoryDetailsApi::class.java)

    suspend fun getRepositoryDetails(
        repositoryOwnerName: String,
        repositoryName: String
    ): Result<GitHubRepositoryDetailsDto, GitHubRepositoriesNetworkError> {
        val response: Response<GitHubRepositoryDetailsDto>

        try {
            response = gitHubRepositoryDetailsApi.getRepositoryDetails(
                ownerName = repositoryOwnerName,
                repositoryName = repositoryName
            )
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