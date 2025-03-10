package com.sweak.githubtrends.features.repository_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.domain.github.GitHubError
import com.sweak.githubtrends.core.domain.github.GitHubRepository
import com.sweak.githubtrends.core.domain.user.UiThemeMode
import com.sweak.githubtrends.core.domain.user.UserDataRepository
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
    private val gitHubRepository: GitHubRepository,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RepositoryListScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            userDataRepository.uiThemeModeFlow.collect {
                _state.update { currentState ->
                    currentState.copy(uiThemeMode = it)
                }
            }
        }

        loadData()
    }

    private fun loadData() = viewModelScope.launch {
        _state.update { currentState ->
            currentState.copy(
                repositoriesUiState = UiState.Loading
            )
        }

        // Half a second delay to prevent abrupt UI state changes:
        delay(500)

        val trendingRepositoriesResult = gitHubRepository.getTrendingRepositories()

        if (trendingRepositoriesResult is Result.Success) {
            if (trendingRepositoriesResult.data.isEmpty()) {
                _state.update { currentState ->
                    currentState.copy(
                        repositoriesUiState = UiState.Error(
                            errorMessage = UiText.StringResource(R.string.unknown_error)
                        )
                    )
                }
                return@launch
            }

            _state.update { currentState ->
                currentState.copy(
                    repositoriesUiState = UiState.Success(
                        trendingRepositoriesResult.data.map {
                            RepositoryPreviewWrapper(
                                id = it.id,
                                name = it.name,
                                username = it.author,
                                description = it.description,
                                language = it.language,
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

    fun onEvent(event: RepositoryListScreenUserEvent) {
        when (event) {
            is RepositoryListScreenUserEvent.TryLoadingAgain -> {
                loadData()
            }
            is RepositoryListScreenUserEvent.ToggleUiTheme -> viewModelScope.launch {
                val newUiThemeMode = when (_state.value.uiThemeMode) {
                    UiThemeMode.LIGHT -> UiThemeMode.DARK
                    UiThemeMode.DARK -> UiThemeMode.LIGHT
                }

                userDataRepository.setUiThemeMode(uiThemeMode = newUiThemeMode)
            }
            else -> { /* no-op */ }
        }
    }
}