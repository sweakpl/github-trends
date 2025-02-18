package com.sweak.githubtrends.features.repository_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sweak.githubtrends.core.designsystem.theme.GitHubTrendsTheme
import com.sweak.githubtrends.core.designsystem.theme.space
import com.sweak.githubtrends.features.repository_list.model.RepositoryPreviewWrapper

@Composable
fun RepositoryCard(
    repositoryPreviewWrapper: RepositoryPreviewWrapper,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier
            .padding(
                horizontal = MaterialTheme.space.medium,
                vertical = MaterialTheme.space.smallMedium
            )
        ) {
            Row(
                modifier.padding(bottom = MaterialTheme.space.small)
            ) {
                Text(text = repositoryPreviewWrapper.username)

                Text(
                    text = "/",
                    modifier = Modifier.padding(horizontal = MaterialTheme.space.xSmall)
                )

                Text(text = repositoryPreviewWrapper.name)
            }

            Text(
                text = repositoryPreviewWrapper.description,
                modifier = Modifier.padding(bottom = MaterialTheme.space.small)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = repositoryPreviewWrapper.totalStars.toString())

                Text(text = repositoryPreviewWrapper.starsSince.toString())
            }
        }
    }
}

@Preview
@Composable
private fun RepositoryCardPreview() {
    GitHubTrendsTheme {
        RepositoryCard(
            repositoryPreviewWrapper = RepositoryPreviewWrapper(
                name = "qralarm-android",
                username = "sweakpl",
                description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                totalStars = 177,
                starsSince = 3
            )
        )
    }
}