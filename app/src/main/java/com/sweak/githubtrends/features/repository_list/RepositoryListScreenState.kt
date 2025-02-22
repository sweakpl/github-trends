package com.sweak.githubtrends.features.repository_list

import com.sweak.githubtrends.core.domain.user.UiThemeMode
import com.sweak.githubtrends.core.ui.util.UiState
import com.sweak.githubtrends.features.repository_list.model.RepositoryPreviewWrapper

data class RepositoryListScreenState(
    val repositoriesUiState: UiState<List<RepositoryPreviewWrapper>> = UiState.Loading,
    val uiThemeMode: UiThemeMode = UiThemeMode.LIGHT
)
