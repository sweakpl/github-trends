package com.sweak.githubtrends.features.repository_list.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sweak.githubtrends.features.repository_list.RepositoryListScreen
import kotlinx.serialization.Serializable

@Serializable
object RepositoryListRoute

fun NavGraphBuilder.repositoryListScreen(
    onRepositoryClicked: (repositoryId: String) -> Unit
) {
    composable<RepositoryListRoute> {
        RepositoryListScreen(
            onRepositoryClicked = onRepositoryClicked
        )
    }
}