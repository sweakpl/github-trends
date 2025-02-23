package com.sweak.githubtrends.features.repository_details

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowWidthSizeClass
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.designsystem.icon.GitHubTrendsIcons
import com.sweak.githubtrends.core.designsystem.theme.GitHubTrendsTheme
import com.sweak.githubtrends.core.designsystem.theme.space
import com.sweak.githubtrends.core.ui.components.ErrorState
import com.sweak.githubtrends.core.ui.util.UiState
import com.sweak.githubtrends.features.repository_details.components.RepositoryDescription
import com.sweak.githubtrends.features.repository_details.components.RepositoryStatCards
import com.sweak.githubtrends.features.repository_details.components.SeeOnGitHubButton
import com.sweak.githubtrends.features.repository_details.model.RepositoryDetailsWrapper

@Composable
fun RepositoryDetailsScreen(
    onBackClicked: () -> Unit
) {
    val repositoryDetailsViewModel: RepositoryDetailsViewModel = hiltViewModel()
    val repositoryDetailsScreenState by repositoryDetailsViewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    RepositoryDetailsScreenContent(
        state = repositoryDetailsScreenState,
        onEvent = { event ->
            when (event) {
                is RepositoryDetailsScreenUserEvent.BackClicked -> {
                    onBackClicked()
                }
                is RepositoryDetailsScreenUserEvent.OpenGitHubClicked -> {
                    try {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(event.repositoryUrl)
                            )
                        )
                    } catch (activityNotFoundException: ActivityNotFoundException) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.issue_opening_the_page),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                else -> {
                    repositoryDetailsViewModel.onEvent(event)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepositoryDetailsScreenContent(
    state: RepositoryDetailsScreenState,
    onEvent: (RepositoryDetailsScreenUserEvent) -> Unit
) {
    val windowSizeClass = currentWindowAdaptiveInfo()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.repository))
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onEvent(RepositoryDetailsScreenUserEvent.BackClicked) }
                    ) {
                        Icon(
                            imageVector = GitHubTrendsIcons.BackArrow,
                            contentDescription = stringResource(
                                R.string.content_description_back_arrow
                            )
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            AnimatedContent(
                targetState = state.repositoryDetailsUiState,
                modifier = Modifier
                    .matchParentSize()
                    .padding(paddingValues = paddingValues),
                label = "contentAnimation"
            ) { repositoryDetailsUiState ->
                when (repositoryDetailsUiState) {
                    is UiState.Success -> {
                        Box(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .padding(all = MaterialTheme.space.mediumLarge),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            if (windowSizeClass.windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.EXPANDED) {
                                CompactMediumRepositoryDetails(
                                    repositoryDetailsUiState = repositoryDetailsUiState,
                                    onOpenGitHubClicked = {
                                        onEvent(
                                            RepositoryDetailsScreenUserEvent.OpenGitHubClicked(
                                                repositoryUrl = repositoryDetailsUiState.data.url
                                            )
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth(
                                        if (windowSizeClass.windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT) {
                                            0.8f
                                        } else 1f
                                    )
                                )
                            } else {
                                ExpandedRepositoryDetails(
                                    repositoryDetailsUiState = repositoryDetailsUiState,
                                    onOpenGitHubClicked = {
                                        onEvent(
                                            RepositoryDetailsScreenUserEvent.OpenGitHubClicked(
                                                repositoryUrl = repositoryDetailsUiState.data.url
                                            )
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth(0.8f)
                                )
                            }
                        }
                    }
                    is UiState.Loading -> {
                        Box(modifier = Modifier.size(size = MaterialTheme.space.xLarge)) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                    is UiState.Error -> {
                        ErrorState(
                            errorMessage = repositoryDetailsUiState.errorMessage.asString(),
                            onTryAgain = {
                                onEvent(RepositoryDetailsScreenUserEvent.TryLoadingAgain)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = MaterialTheme.space.large)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CompactMediumRepositoryDetails(
    repositoryDetailsUiState: UiState.Success<RepositoryDetailsWrapper>,
    onOpenGitHubClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        RepositoryDescription(
            name = repositoryDetailsUiState.data.name,
            userName = repositoryDetailsUiState.data.userName,
            usernameAvatarUrl = repositoryDetailsUiState.data.url,
            description = repositoryDetailsUiState.data.description,
            createdAt = repositoryDetailsUiState.data.createdAt,
            updatedAt = repositoryDetailsUiState.data.updatedAt,
            modifier = Modifier.padding(bottom = MaterialTheme.space.mediumLarge)
        )

        RepositoryStatCards(
            repositoryStats = repositoryDetailsUiState.data.repositoryStats,
            modifier = Modifier.padding(bottom = MaterialTheme.space.mediumLarge)
        )

        SeeOnGitHubButton(onClick = onOpenGitHubClicked)
    }
}

@Composable
private fun ExpandedRepositoryDetails(
    repositoryDetailsUiState: UiState.Success<RepositoryDetailsWrapper>,
    onOpenGitHubClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.space.large),
        modifier = modifier
    ) {
        Column(modifier = Modifier.weight(1f)) {
            RepositoryDescription(
                name = repositoryDetailsUiState.data.name,
                userName = repositoryDetailsUiState.data.userName,
                usernameAvatarUrl = repositoryDetailsUiState.data.url,
                description = repositoryDetailsUiState.data.description,
                createdAt = repositoryDetailsUiState.data.createdAt,
                updatedAt = repositoryDetailsUiState.data.updatedAt,
                modifier = Modifier.padding(bottom = MaterialTheme.space.mediumLarge)
            )

            SeeOnGitHubButton(onClick = onOpenGitHubClicked)
        }

        RepositoryStatCards(
            repositoryStats = repositoryDetailsUiState.data.repositoryStats,
            modifier = Modifier.weight(1f)
        )
    }
}

@PreviewScreenSizes
@Preview
@Composable
private fun RepositoryDetailsScreenContentPreview() {
    GitHubTrendsTheme {
        RepositoryDetailsScreenContent(
            state = RepositoryDetailsScreenState(
                repositoryDetailsUiState = UiState.Success(
                    RepositoryDetailsWrapper(
                        name = "qralarm-android",
                        userName = "sweakpl",
                        usernameAvatarUrl = "https://avatars.githubusercontent.com/u/70141120?v=4",
                        createdAt = 1647771066000,
                        updatedAt = 1740168725000,
                        description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                        url = "https://github.com/sweakpl/qralarm-android",
                        repositoryStats = listOf(
                            RepositoryDetailsWrapper.SingleStatWrapper.Stars(177),
                            RepositoryDetailsWrapper.SingleStatWrapper.Language("Kotlin"),
                            RepositoryDetailsWrapper.SingleStatWrapper.Forks(14),
                            RepositoryDetailsWrapper.SingleStatWrapper.Watchers(7),
                            RepositoryDetailsWrapper.SingleStatWrapper.Issues(7),
                            RepositoryDetailsWrapper.SingleStatWrapper.License("GPL-3.0")
                        )
                    )
                )
            ),
            onEvent = { /* no-op */ }
        )
    }
}