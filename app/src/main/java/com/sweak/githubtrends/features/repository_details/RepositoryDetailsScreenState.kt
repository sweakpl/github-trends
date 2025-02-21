package com.sweak.githubtrends.features.repository_details

import com.sweak.githubtrends.core.ui.util.UiState
import com.sweak.githubtrends.features.repository_details.model.RepositoryDetailsWrapper

data class RepositoryDetailsScreenState(
    val repositoryDetailsUiState: UiState<RepositoryDetailsWrapper>
)
