package com.sweak.githubtrends.features.repository_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.designsystem.icon.GitHubTrendsIcons
import com.sweak.githubtrends.core.designsystem.theme.GitHubTrendsTheme
import com.sweak.githubtrends.core.designsystem.theme.space
import com.sweak.githubtrends.features.repository_details.model.RepositoryDetailsWrapper

@Composable
fun RepositoryDetailsScreen(
    onBackClicked: () -> Unit
) {
    val repositoryDetailsViewModel: RepositoryDetailsViewModel = viewModel()
    val repositoryDetailsScreenState by repositoryDetailsViewModel.state.collectAsStateWithLifecycle()

    RepositoryDetailsScreenContent(
        state = repositoryDetailsScreenState,
        onEvent = { event ->
            when (event) {
                is RepositoryDetailsScreenUserEvent.BackClicked -> {
                    onBackClicked()
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
        Box(
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(all = MaterialTheme.space.medium)
            ) {
                if (state.repositoryDetailsWrapper != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = MaterialTheme.space.medium)
                    ) {
                        Text(
                            text = state.repositoryDetailsWrapper.username,
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Text(
                            text = "/",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(horizontal = MaterialTheme.space.xSmall)
                        )

                        Text(
                            text = state.repositoryDetailsWrapper.name,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 28.sp
                            )
                        )
                    }

                    Text(
                        text = state.repositoryDetailsWrapper.description,
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
                                text = state.repositoryDetailsWrapper.totalStars.toString(),
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
                                text = state.repositoryDetailsWrapper.starsSince.toString(),
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
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
                repositoryDetailsWrapper = RepositoryDetailsWrapper(
                    name = "qralarm-android",
                    username = "sweakpl",
                    description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                    totalStars = 177,
                    starsSince = 3
                )
            ),
            onEvent = { /* no-op */ }
        )
    }
}