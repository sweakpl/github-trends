package com.sweak.githubtrends.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sweak.githubtrends.core.designsystem.theme.GitHubTrendsTheme
import com.sweak.githubtrends.core.domain.user.UiThemeMode
import com.sweak.githubtrends.core.domain.user.UserDataRepository
import com.sweak.githubtrends.features.repository_details.navigation.navigateToRepositoryDetails
import com.sweak.githubtrends.features.repository_details.navigation.repositoryDetailsScreen
import com.sweak.githubtrends.features.repository_list.navigation.RepositoryListRoute
import com.sweak.githubtrends.features.repository_list.navigation.repositoryListScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var userDataRepository: UserDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        var shouldShowSplashScreen by mutableStateOf(true)
        splashScreen.setKeepOnScreenCondition { shouldShowSplashScreen }

        enableEdgeToEdge()

        setContent {
            val uiThemeMode by userDataRepository.uiThemeModeFlow
                .onEach { shouldShowSplashScreen = false }
                .collectAsStateWithLifecycle(initialValue = UiThemeMode.LIGHT)

            GitHubTrendsTheme(uiThemeMode = uiThemeMode) {
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