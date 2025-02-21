package com.sweak.githubtrends.features.repository_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.githubtrends.core.domain.GitHubRepository
import com.sweak.githubtrends.core.domain.util.Result
import com.sweak.githubtrends.features.repository_list.model.RepositoryPreviewWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryListViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository
) : ViewModel() {
    val state = MutableStateFlow(RepositoryListScreenState())

    init {
        viewModelScope.launch {
            // TODO: handle loading and error states

            val trendingRepositoriesResult = gitHubRepository.getTrendingRepositories()

            if (trendingRepositoriesResult is Result.Success) {
                state.update { currentState ->
                    currentState.copy(
                        repositories = trendingRepositoriesResult.data.map {
                            RepositoryPreviewWrapper(
                                id = it.id,
                                name = it.name,
                                username = it.author,
                                description = it.description ?: "", // TODO: handle no description
                                totalStars = it.stars,
                                starsSince = it.starsSince
                            )
                        }
                    )
                }
            }
        }
    }
}