package com.sweak.githubtrends.features.repository_list

sealed class RepositoryListScreenUserEvent {
    data class RepositoryClicked(val repositoryId: Long) : RepositoryListScreenUserEvent()
}