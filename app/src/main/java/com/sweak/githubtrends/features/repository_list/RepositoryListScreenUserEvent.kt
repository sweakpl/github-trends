package com.sweak.githubtrends.features.repository_list

sealed class RepositoryListScreenUserEvent {
    data class RepositoryClicked(val repositoryId: String) : RepositoryListScreenUserEvent()
    data object TryLoadingAgain : RepositoryListScreenUserEvent()
    data object ToggleUiTheme : RepositoryListScreenUserEvent()
}