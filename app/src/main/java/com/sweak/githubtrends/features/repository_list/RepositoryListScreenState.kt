package com.sweak.githubtrends.features.repository_list

import com.sweak.githubtrends.features.repository_list.model.RepositoryPreviewWrapper

data class RepositoryListScreenState(
    val repositories: List<RepositoryPreviewWrapper>
)
