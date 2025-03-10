package com.sweak.githubtrends.features.repository_details.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sweak.githubtrends.features.repository_details.RepositoryDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryDetailsRoute(
    val repositoryId: String
)

fun NavController.navigateToRepositoryDetails(
    repositoryId: String
) = navigate(
    route = RepositoryDetailsRoute(
        repositoryId = repositoryId
    )
)

fun NavGraphBuilder.repositoryDetailsScreen(
    onBackClicked: () -> Unit
) {
    composable<RepositoryDetailsRoute> {
        RepositoryDetailsScreen(
            onBackClicked = onBackClicked
        )
    }
}