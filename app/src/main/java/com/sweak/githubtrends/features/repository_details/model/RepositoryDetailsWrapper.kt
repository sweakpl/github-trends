package com.sweak.githubtrends.features.repository_details.model

data class RepositoryDetailsWrapper(
    val name: String,
    val username: String,
    val usernameAvatarUrl: String,
    val createdAt: Long?,
    val updatedAt: Long?,
    val description: String?,
    val url: String,
    val repositoryStats: List<SingleStatWrapper>
) {
    sealed class SingleStatWrapper {
        data class Stars(val starsAmount: Int) : SingleStatWrapper()
        data class Language(val languageName: String) : SingleStatWrapper()
        data class Forks(val forksAmount: Int) : SingleStatWrapper()
        data class Watchers(val watchersAmount: Int) : SingleStatWrapper()
        data class Issues(val openIssuesAmount: Int) : SingleStatWrapper()
        data class License(val licenseName: String) : SingleStatWrapper()
    }
}
