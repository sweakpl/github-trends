package com.sweak.githubtrends.features.repository_details

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.designsystem.icon.GitHubTrendsIcons
import com.sweak.githubtrends.core.designsystem.theme.GitHubTrendsTheme
import com.sweak.githubtrends.core.designsystem.theme.space
import com.sweak.githubtrends.core.ui.components.ErrorState
import com.sweak.githubtrends.core.ui.util.UiState
import com.sweak.githubtrends.features.repository_details.model.RepositoryDetailsWrapper

@Composable
fun RepositoryDetailsScreen(
    onBackClicked: () -> Unit
) {
    val repositoryDetailsViewModel: RepositoryDetailsViewModel = hiltViewModel()
    val repositoryDetailsScreenState by repositoryDetailsViewModel.state.collectAsStateWithLifecycle()

    RepositoryDetailsScreenContent(
        state = repositoryDetailsScreenState,
        onEvent = { event ->
            when (event) {
                is RepositoryDetailsScreenUserEvent.BackClicked -> {
                    onBackClicked()
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
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .padding(all = MaterialTheme.space.medium)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = MaterialTheme.space.medium)
                            ) {
                                Text(
                                    text = repositoryDetailsUiState.data.username,
                                    style = MaterialTheme.typography.headlineSmall
                                )

                                Text(
                                    text = "/",
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(horizontal = MaterialTheme.space.xSmall)
                                )

                                Text(
                                    text = repositoryDetailsUiState.data.name,
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 28.sp
                                    )
                                )
                            }

                            Text(
                                text = repositoryDetailsUiState.data.description,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = MaterialTheme.space.medium),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(
                                        space = MaterialTheme.space.xSmall
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = GitHubTrendsIcons.Star,
                                        contentDescription = stringResource(
                                            R.string.content_description_star
                                        ),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(size = MaterialTheme.space.large)
                                    )

                                    Text(
                                        text = repositoryDetailsUiState.data.totalStars.toString(),
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(
                                        space = MaterialTheme.space.xSmall
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = GitHubTrendsIcons.Growth,
                                        contentDescription = stringResource(
                                            R.string.content_description_growth
                                        ),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(size = MaterialTheme.space.large)
                                    )

                                    Text(
                                        text = repositoryDetailsUiState.data.starsSince.toString(),
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
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

@Preview
@Composable
private fun RepositoryDetailsScreenContentPreview() {
    GitHubTrendsTheme {
        RepositoryDetailsScreenContent(
            state = RepositoryDetailsScreenState(
                repositoryDetailsUiState = UiState.Success(
                    RepositoryDetailsWrapper(
                        name = "qralarm-android",
                        username = "sweakpl",
                        description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                        totalStars = 177,
                        starsSince = 3
                    )
                )
            ),
            onEvent = { /* no-op */ }
        )
    }
}