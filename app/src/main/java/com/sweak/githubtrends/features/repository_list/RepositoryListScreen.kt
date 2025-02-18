package com.sweak.githubtrends.features.repository_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.designsystem.theme.GitHubTrendsTheme
import com.sweak.githubtrends.core.designsystem.theme.space
import com.sweak.githubtrends.features.repository_list.components.RepositoryCard
import com.sweak.githubtrends.features.repository_list.model.RepositoryPreviewWrapper

@Composable
fun RepositoryListScreen() {
    RepositoryListScreenContent(
        state = RepositoryListScreenState(
            repositories = listOf(
                RepositoryPreviewWrapper(
                    name = "qralarm-android",
                    username = "sweakpl",
                    description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                    totalStars = 177,
                    starsSince = 3
                ),
                RepositoryPreviewWrapper(
                    name = "qralarm-android",
                    username = "sweakpl",
                    description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                    totalStars = 177,
                    starsSince = 3
                ),
                RepositoryPreviewWrapper(
                    name = "qralarm-android",
                    username = "sweakpl",
                    description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                    totalStars = 177,
                    starsSince = 3
                )
            )
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepositoryListScreenContent(
    state: RepositoryListScreenState
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.trending)
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.space.medium),
            contentPadding = PaddingValues(all = MaterialTheme.space.medium),
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            items(state.repositories) {
                RepositoryCard(repositoryPreviewWrapper = it)
            }
        }
    }
}

@Preview
@Composable
private fun RepositoryListScreenContentPreview() {
    GitHubTrendsTheme {
        RepositoryListScreenContent(
            state = RepositoryListScreenState(
                repositories = listOf(
                    RepositoryPreviewWrapper(
                        name = "qralarm-android",
                        username = "sweakpl",
                        description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                        totalStars = 177,
                        starsSince = 3
                    ),
                    RepositoryPreviewWrapper(
                        name = "qralarm-android",
                        username = "sweakpl",
                        description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                        totalStars = 177,
                        starsSince = 3
                    ),
                    RepositoryPreviewWrapper(
                        name = "qralarm-android",
                        username = "sweakpl",
                        description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                        totalStars = 177,
                        starsSince = 3
                    )
                )
            )
        )
    }
}