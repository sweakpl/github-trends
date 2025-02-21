package com.sweak.githubtrends.features.repository_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.domain.GitHubError
import com.sweak.githubtrends.core.domain.GitHubRepository
import com.sweak.githubtrends.core.domain.util.Result
import com.sweak.githubtrends.core.ui.util.UiState
import com.sweak.githubtrends.core.ui.util.UiText
import com.sweak.githubtrends.features.repository_list.model.RepositoryPreviewWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryListViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        RepositoryListScreenState(
            repositoriesUiState = UiState.Loading
        )
    )
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    repositoriesUiState = UiState.Loading
                )
            }

            // Half a second delay to prevent abrupt UI state changes:
            delay(500)

            val trendingRepositoriesResult = gitHubRepository.getTrendingRepositories()

            if (trendingRepositoriesResult is Result.Success) {
                // TODO: handle empty data (sometimes it can happen)

                _state.update { currentState ->
                    currentState.copy(
                        repositoriesUiState = UiState.Success(
                            trendingRepositoriesResult.data.map {
                                RepositoryPreviewWrapper(
                                    id = it.id,
                                    name = it.name,
                                    username = it.author,
                                    description = it.description
                                        ?: "", // TODO: handle no description
                                    totalStars = it.stars,
                                    starsSince = it.starsSince
                                )
                            }
                        )
                    )
                }
            } else if (trendingRepositoriesResult is Result.Error) {
                _state.update { currentState ->
                    currentState.copy(
                        repositoriesUiState = UiState.Error(
                            errorMessage = UiText.StringResource(
                                when (trendingRepositoriesResult.error) {
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
    }

    fun onEvent(event: RepositoryListScreenUserEvent) {
        when (event) {
            is RepositoryListScreenUserEvent.TryLoadingAgain -> {
                loadData()
            }
            else -> { /* no-op */ }
        }
    }
}