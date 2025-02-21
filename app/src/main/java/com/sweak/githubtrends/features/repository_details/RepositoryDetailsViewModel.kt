package com.sweak.githubtrends.features.repository_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.domain.GitHubError
import com.sweak.githubtrends.core.domain.GitHubRepository
import com.sweak.githubtrends.core.domain.util.Result
import com.sweak.githubtrends.core.ui.util.UiState
import com.sweak.githubtrends.core.ui.util.UiText
import com.sweak.githubtrends.features.repository_details.model.RepositoryDetailsWrapper
import com.sweak.githubtrends.features.repository_details.navigation.RepositoryDetailsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gitHubRepository: GitHubRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        RepositoryDetailsScreenState(
            repositoryDetailsUiState = UiState.Loading
        )
    )
    val state = _state.asStateFlow()

    private val repositoryDetailsRoute = savedStateHandle.toRoute<RepositoryDetailsRoute>()

    init {
        loadData()
    }

    private fun loadData() = viewModelScope.launch {
        _state.update { currentState ->
            currentState.copy(
                repositoryDetailsUiState = UiState.Loading
            )
        }

        // Half a second delay to prevent abrupt UI state changes:
        delay(500)

        val repositoryDetailsResult = gitHubRepository.getRepositoryDetails(
            repositoryId = repositoryDetailsRoute.repositoryId
        )

        if (repositoryDetailsResult is Result.Success) {
            _state.update { currentState ->
                currentState.copy(
                    repositoryDetailsUiState = UiState.Success(
                        RepositoryDetailsWrapper(
                            name = repositoryDetailsResult.data.name,
                            username = repositoryDetailsResult.data.author,
                            description = repositoryDetailsResult.data.description ?: "", // TODO: handle no description
                            totalStars = repositoryDetailsResult.data.stars,
                            starsSince = 3 // TODO: remove stars since
                        )
                    )
                )
            }
        } else if (repositoryDetailsResult is Result.Error) {
            _state.update { currentState ->
                currentState.copy(
                    repositoryDetailsUiState = UiState.Error(
                        errorMessage = UiText.StringResource(
                            when (repositoryDetailsResult.error) {
                                GitHubError.CONNECTION_ERROR -> R.string.connection_error
                                GitHubError.SERVER_ERROR -> R.string.server_error
                                GitHubError.UNKNOWN -> R.string.unknown_error
                            }
                        )
                    )
                )
            }
        }
    }

    fun onEvent(event: RepositoryDetailsScreenUserEvent) {
        when (event) {
            is RepositoryDetailsScreenUserEvent.TryLoadingAgain -> {
                loadData()
            }
            else -> { /* no-op */ }
        }
    }
}