package com.sweak.githubtrends.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sweak.githubtrends.core.designsystem.theme.GitHubTrendsTheme
import com.sweak.githubtrends.features.repository_details.navigation.navigateToRepositoryDetails
import com.sweak.githubtrends.features.repository_details.navigation.repositoryDetailsScreen
import com.sweak.githubtrends.features.repository_list.navigation.RepositoryListRoute
import com.sweak.githubtrends.features.repository_list.navigation.repositoryListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            GitHubTrendsTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = RepositoryListRoute
                ) {
                    repositoryListScreen(
                        onRepositoryClicked = { repositoryId ->
                            navController.navigateToRepositoryDetails(
                                repositoryId = repositoryId
                            )
                        }
                    )

                    repositoryDetailsScreen(
                        onBackClicked = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}