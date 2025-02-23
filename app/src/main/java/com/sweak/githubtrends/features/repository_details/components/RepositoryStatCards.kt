package com.sweak.githubtrends.features.repository_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.window.core.layout.WindowWidthSizeClass
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.designsystem.icon.GitHubTrendsIcons
import com.sweak.githubtrends.core.designsystem.theme.space
import com.sweak.githubtrends.core.ui.util.LanguageColors
import com.sweak.githubtrends.features.repository_details.model.RepositoryDetailsWrapper

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun RepositoryStatCards(
    repositoryStats: List<RepositoryDetailsWrapper.SingleStatWrapper>,
    modifier: Modifier = Modifier
) {
    val windowSizeClass = currentWindowAdaptiveInfo()

    val maxItemsInRow =
        when (windowSizeClass.windowSizeClass.windowWidthSizeClass) {
            WindowWidthSizeClass.MEDIUM -> 3
            else -> 2
        }

    FlowRow(
        horizontalArrangement =
        Arrangement.spacedBy(MaterialTheme.space.medium),
        verticalArrangement =
        Arrangement.spacedBy(MaterialTheme.space.medium),
        maxItemsInEachRow = maxItemsInRow,
        modifier = modifier
    ) {
        repositoryStats.forEach { statWrapper ->
            when (statWrapper) {
                is RepositoryDetailsWrapper.SingleStatWrapper.Stars -> {
                    StatCard(
                        title = stringResource(R.string.stars),
                        icon = {
                            Icon(
                                imageVector = GitHubTrendsIcons.Star,
                                contentDescription = stringResource(
                                    R.string.content_description_star
                                ),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .size(size = MaterialTheme.space.large)
                            )
                        },
                        value = statWrapper.starsAmount.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }
                is RepositoryDetailsWrapper.SingleStatWrapper.Language -> {
                    StatCard(
                        title = stringResource(R.string.language),
                        icon = {
                            LanguageColors[statWrapper.languageName]?.let {
                                Box(
                                    modifier = Modifier
                                        .size(size = MaterialTheme.space.large)
                                        .clip(shape = CircleShape)
                                        .background(color = it)
                                )
                            }
                        },
                        value = statWrapper.languageName,
                        modifier = Modifier.weight(1f)
                    )
                }
                is RepositoryDetailsWrapper.SingleStatWrapper.Forks -> {
                    StatCard(
                        title = stringResource(R.string.forks),
                        icon = {
                            Icon(
                                imageVector = GitHubTrendsIcons.Fork,
                                contentDescription = stringResource(
                                    R.string.content_description_fork
                                ),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .size(size = MaterialTheme.space.large)
                            )
                        },
                        value = statWrapper.forksAmount.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }
                is RepositoryDetailsWrapper.SingleStatWrapper.Watchers -> {
                    StatCard(
                        title = stringResource(R.string.watching),
                        icon = {
                            Icon(
                                imageVector = GitHubTrendsIcons.Watch,
                                contentDescription = stringResource(
                                    R.string.content_description_watch
                                ),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .size(size = MaterialTheme.space.large)
                            )
                        },
                        value = statWrapper.watchersAmount.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }
                is RepositoryDetailsWrapper.SingleStatWrapper.Issues -> {
                    StatCard(
                        title = stringResource(R.string.issues),
                        icon = {
                            Icon(
                                imageVector = GitHubTrendsIcons.Issue,
                                contentDescription = stringResource(
                                    R.string.content_description_issue
                                ),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .size(size = MaterialTheme.space.large)
                            )
                        },
                        value = statWrapper.openIssuesAmount.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }
                is RepositoryDetailsWrapper.SingleStatWrapper.License -> {
                    StatCard(
                        title = stringResource(R.string.license),
                        icon = {
                            Icon(
                                imageVector = GitHubTrendsIcons.License,
                                contentDescription = stringResource(
                                    R.string.content_description_license
                                ),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .size(size = MaterialTheme.space.large)
                            )
                        },
                        value = statWrapper.licenseName,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        val leftOverCardSlots = repositoryStats.size % maxItemsInRow
        val weight = (maxItemsInRow - leftOverCardSlots).toFloat()

        if (weight != maxItemsInRow.toFloat()) {
            // Adding weighted spacer to prevent StatCards taking
            // too much space if space is left over:
            Spacer(modifier = Modifier.weight(weight))
        }
    }
}
