package com.sweak.githubtrends.features.repository_list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.designsystem.icon.GitHubTrendsIcons
import com.sweak.githubtrends.core.designsystem.theme.GitHubTrendsTheme
import com.sweak.githubtrends.core.designsystem.theme.space
import com.sweak.githubtrends.core.ui.components.ErrorState
import com.sweak.githubtrends.core.ui.util.UiState
import com.sweak.githubtrends.features.repository_list.components.RepositoryCard
import com.sweak.githubtrends.features.repository_list.model.RepositoryPreviewWrapper

@Composable
fun RepositoryListScreen(
    onRepositoryClicked: (repositoryId: String) -> Unit
) {
    val repositoryListViewModel: RepositoryListViewModel = hiltViewModel()
    val repositoryListScreenState by repositoryListViewModel.state.collectAsStateWithLifecycle()

    RepositoryListScreenContent(
        state = repositoryListScreenState,
        onEvent = { event ->
            when (event) {
                is RepositoryListScreenUserEvent.RepositoryClicked -> {
                    onRepositoryClicked(event.repositoryId)
                }
                else -> {
                    repositoryListViewModel.onEvent(event)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepositoryListScreenContent(
    state: RepositoryListScreenState,
    onEvent: (RepositoryListScreenUserEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.space.small),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = GitHubTrendsIcons.GitHub,
                            contentDescription = stringResource(R.string.content_description_github),
                            modifier = Modifier.size(size = MaterialTheme.space.large)
                        )

                        Text(
                            text = stringResource(R.string.trending)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            AnimatedContent(
                targetState = state.repositoriesUiState,
                modifier = Modifier
                    .matchParentSize()
                    .padding(paddingValues = paddingValues),
                label = "contentAnimation"
            ) { repositoriesUiState ->
                when (repositoriesUiState) {
                    is UiState.Success -> {
                        LazyColumn(
                            verticalArrangement = Arrangement
                                .spacedBy(space = MaterialTheme.space.medium),
                            contentPadding = PaddingValues(all = MaterialTheme.space.medium)
                        ) {
                            items(repositoriesUiState.data) { repositoryPreviewWrapper ->
                                RepositoryCard(
                                    repositoryPreviewWrapper = repositoryPreviewWrapper,
                                    onClick = { repositoryId ->
                                        onEvent(
                                            RepositoryListScreenUserEvent.RepositoryClicked(
                                                repositoryId = repositoryId
                                            )
                                        )
                                    }
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
                            errorMessage = repositoriesUiState.errorMessage.asString(),
                            onTryAgain = {
                                onEvent(RepositoryListScreenUserEvent.TryLoadingAgain)
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

@Preview
@Composable
private fun RepositoryListScreenContentPreview() {
    GitHubTrendsTheme(darkTheme = true) {
        RepositoryListScreenContent(
            state = RepositoryListScreenState(
                repositoriesUiState = UiState.Success(
                    listOf(
                        RepositoryPreviewWrapper(
                            id = "sweakpl/qralarm-android",
                            name = "qralarm-android",
                            username = "sweakpl",
                            description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                            totalStars = 177,
                            starsSince = 3
                        ),
                        RepositoryPreviewWrapper(
                            id = "sweakpl/qralarm-android",
                            name = "qralarm-android",
                            username = "sweakpl",
                            description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                            totalStars = 177,
                            starsSince = 3
                        ),
                        RepositoryPreviewWrapper(
                            id = "sweakpl/qralarm-android",
                            name = "qralarm-android",
                            username = "sweakpl",
                            description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                            totalStars = 177,
                            starsSince = 3
                        )
                    )
                )
            ),
            onEvent = { /* no-op */ }
        )
    }
}