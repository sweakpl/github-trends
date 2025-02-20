package com.sweak.githubtrends.features.repository_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.designsystem.icon.GitHubTrendsIcons
import com.sweak.githubtrends.core.designsystem.theme.GitHubTrendsTheme
import com.sweak.githubtrends.core.designsystem.theme.space
import com.sweak.githubtrends.features.repository_list.components.RepositoryCard
import com.sweak.githubtrends.features.repository_list.model.RepositoryPreviewWrapper

@Composable
fun RepositoryListScreen(
    onRepositoryClicked: (repositoryId: Long) -> Unit
) {
    RepositoryListScreenContent(
        state = RepositoryListScreenState(
            repositories = listOf(
                RepositoryPreviewWrapper(
                    id = 0,
                    name = "qralarm-android",
                    username = "sweakpl",
                    description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                    totalStars = 177,
                    starsSince = 3
                ),
                RepositoryPreviewWrapper(
                    id = 0,
                    name = "qralarm-android",
                    username = "sweakpl",
                    description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                    totalStars = 177,
                    starsSince = 3
                ),
                RepositoryPreviewWrapper(
                    id = 0,
                    name = "qralarm-android",
                    username = "sweakpl",
                    description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                    totalStars = 177,
                    starsSince = 3
                )
            )
        ),
        onEvent = { event ->
            when (event) {
                is RepositoryListScreenUserEvent.RepositoryClicked -> {
                    onRepositoryClicked(event.repositoryId)
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
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.space.medium),
            contentPadding = PaddingValues(all = MaterialTheme.space.medium),
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            items(state.repositories) { repositoryPreviewWrapper ->
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
}

@Preview
@Composable
private fun RepositoryListScreenContentPreview() {
    GitHubTrendsTheme(darkTheme = true) {
        RepositoryListScreenContent(
            state = RepositoryListScreenState(
                repositories = listOf(
                    RepositoryPreviewWrapper(
                        id = 0,
                        name = "qralarm-android",
                        username = "sweakpl",
                        description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                        totalStars = 177,
                        starsSince = 3
                    ),
                    RepositoryPreviewWrapper(
                        id = 0,
                        name = "qralarm-android",
                        username = "sweakpl",
                        description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                        totalStars = 177,
                        starsSince = 3
                    ),
                    RepositoryPreviewWrapper(
                        id = 0,
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