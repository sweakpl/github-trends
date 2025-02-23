package com.sweak.githubtrends.features.repository_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.domain.github.GitHubError
import com.sweak.githubtrends.core.domain.github.GitHubRepository
import com.sweak.githubtrends.core.domain.util.Result
import com.sweak.githubtrends.core.ui.util.UiState
import com.sweak.githubtrends.core.ui.util.UiText
import com.sweak.githubtrends.features.repository_details.model.RepositoryDetailsWrapper
import com.sweak.githubtrends.features.repository_details.model.RepositoryDetailsWrapper.SingleStatWrapper
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
                            usernameAvatarUrl = repositoryDetailsResult.data.authorAvatarUrl,
                            createdAt = repositoryDetailsResult.data.createdAt,
                            updatedAt = repositoryDetailsResult.data.updatedAt,
                            description = repositoryDetailsResult.data.description,
                            url = repositoryDetailsResult.data.url,
                            repositoryStats = buildList {
                                add(
                                    SingleStatWrapper.Stars(
                                        starsAmount = repositoryDetailsResult.data.stars
                                    )
                                )
                                repositoryDetailsResult.data.language?.let {
                                    add(SingleStatWrapper.Language(languageName = it))
                                }
                                add(
                                    SingleStatWrapper.Forks(
                                        forksAmount = repositoryDetailsResult.data.forks
                                    )
                                )
                                add(
                                    SingleStatWrapper.Watchers(
                                        watchersAmount = repositoryDetailsResult.data.watchers
                                    )
                                )
                                add(
                                    SingleStatWrapper.Issues(
                                        openIssuesAmount = repositoryDetailsResult.data.openIssues
                                    )
                                )
                                repositoryDetailsResult.data.license?.let {
                                    add(SingleStatWrapper.License(licenseName = it))
                                }
                            }
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