package com.sweak.githubtrends.features.repository_details

sealed class RepositoryDetailsScreenUserEvent {
    data object BackClicked : RepositoryDetailsScreenUserEvent()
    data object TryLoadingAgain : RepositoryDetailsScreenUserEvent()
}