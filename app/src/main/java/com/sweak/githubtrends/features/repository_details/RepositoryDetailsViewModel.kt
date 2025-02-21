package com.sweak.githubtrends.features.repository_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sweak.githubtrends.core.domain.GitHubRepository
import com.sweak.githubtrends.core.domain.util.Result
import com.sweak.githubtrends.features.repository_details.model.RepositoryDetailsWrapper
import com.sweak.githubtrends.features.repository_details.navigation.RepositoryDetailsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gitHubRepository: GitHubRepository
) : ViewModel() {
    val state = MutableStateFlow(RepositoryDetailsScreenState())

    private val repositoryDetailsRoute = savedStateHandle.toRoute<RepositoryDetailsRoute>()

    init {
        viewModelScope.launch {
            // TODO: handle loading and error states

            val repositoryDetailsResult = gitHubRepository.getRepositoryDetails(
                repositoryId = repositoryDetailsRoute.repositoryId
            )

            if (repositoryDetailsResult is Result.Success) {
                state.update { currentState ->
                    currentState.copy(
                        repositoryDetailsWrapper =
                        RepositoryDetailsWrapper(
                            name = repositoryDetailsResult.data.name,
                            username = repositoryDetailsResult.data.author,
                            description = repositoryDetailsResult.data.description ?: "", // TODO: handle no description
                            totalStars = repositoryDetailsResult.data.stars,
                            starsSince = 3 // TODO: remove stars since
                        )
                    )
                }
            }
        }
    }
}