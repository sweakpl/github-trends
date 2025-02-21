package com.sweak.githubtrends.features.repository_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.designsystem.icon.GitHubTrendsIcons
import com.sweak.githubtrends.core.designsystem.theme.GitHubTrendsTheme
import com.sweak.githubtrends.core.designsystem.theme.space
import com.sweak.githubtrends.core.ui.util.LanguageColors
import com.sweak.githubtrends.features.repository_list.model.RepositoryPreviewWrapper

@Composable
fun RepositoryCard(
    repositoryPreviewWrapper: RepositoryPreviewWrapper,
    onClick: (repositoryId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Wrapping the Card with Surface to prevent the click ripple effect leak outside of the Card:
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier.clickable {
                onClick(repositoryPreviewWrapper.id)
            }
        ) {
            Column(modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.space.medium,
                    vertical = MaterialTheme.space.smallMedium
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = MaterialTheme.space.small)
                ) {
                    Text(
                        text = repositoryPreviewWrapper.username,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "/",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = MaterialTheme.space.xSmall)
                    )

                    Text(
                        text = repositoryPreviewWrapper.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                if (repositoryPreviewWrapper.description != null) {
                    Text(
                        text = repositoryPreviewWrapper.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = MaterialTheme.space.small),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.space.mediumLarge),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (repositoryPreviewWrapper.language != null) {
                        Row(
                            horizontalArrangement =
                            Arrangement.spacedBy(MaterialTheme.space.xSmall),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            LanguageColors[repositoryPreviewWrapper.language]?.let {
                                Box(
                                    modifier = Modifier
                                        .size(size = MaterialTheme.space.smallMedium)
                                        .clip(shape = CircleShape)
                                        .background(color = it)
                                )
                            }

                            Text(
                                text = repositoryPreviewWrapper.language,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.space.xSmall),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = GitHubTrendsIcons.Star,
                            contentDescription = stringResource(R.string.content_description_star),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = repositoryPreviewWrapper.totalStars.toString(),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.space.xSmall),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = GitHubTrendsIcons.Growth,
                            contentDescription = stringResource(R.string.content_description_growth),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = repositoryPreviewWrapper.starsSince.toString(),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun RepositoryCardPreview() {
    GitHubTrendsTheme(darkTheme = true) {
        RepositoryCard(
            repositoryPreviewWrapper = RepositoryPreviewWrapper(
                id = "sweakpl/qralarm-android",
                name = "qralarm-android",
                username = "sweakpl",
                description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                language = "Kotlin",
                totalStars = 177,
                starsSince = 3
            ),
            onClick = { /* no-op */ }
        )
    }
}