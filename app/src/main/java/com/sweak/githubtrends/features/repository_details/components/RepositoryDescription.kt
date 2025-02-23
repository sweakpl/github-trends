package com.sweak.githubtrends.features.repository_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.designsystem.theme.space
import com.sweak.githubtrends.core.ui.util.UiState
import com.sweak.githubtrends.features.repository_details.model.RepositoryDetailsWrapper
import com.sweak.githubtrends.features.repository_details.util.getFormattedDate

@Composable
fun RepositoryDescription(
    repositoryDetailsUiState: UiState.Success<RepositoryDetailsWrapper>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = MaterialTheme.space.small)
        ) {
            AsyncImage(
                model = repositoryDetailsUiState.data.usernameAvatarUrl,
                contentDescription = stringResource(
                    R.string.content_description_repository_owner_avatar
                ),
                modifier = Modifier.size(size = MaterialTheme.space.large)
            )

            Text(
                text = repositoryDetailsUiState.data.username,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = MaterialTheme.space.small)
            )
        }

        Text(
            text = repositoryDetailsUiState.data.name,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp
            ),
            modifier = Modifier
                .padding(bottom = MaterialTheme.space.mediumLarge)
        )

        if (repositoryDetailsUiState.data.description != null) {
            Text(
                text = repositoryDetailsUiState.data.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = MaterialTheme.space.medium),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (repositoryDetailsUiState.data.createdAt != null) {
            Row(
                modifier = Modifier.padding(bottom = MaterialTheme.space.xSmall)
            ) {
                Text(
                    text = stringResource(R.string.created_at_colon),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(end = MaterialTheme.space.small)
                )

                Text(
                    text = getFormattedDate(
                        timestamp = repositoryDetailsUiState.data.createdAt
                    ),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        if (repositoryDetailsUiState.data.updatedAt != null) {
            Row {
                Text(
                    text = stringResource(R.string.updated_at_colon),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(end = MaterialTheme.space.small)
                )

                Text(
                    text = getFormattedDate(
                        timestamp = repositoryDetailsUiState.data.updatedAt
                    ),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
