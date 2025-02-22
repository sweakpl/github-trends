package com.sweak.githubtrends.features.repository_details

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.designsystem.icon.GitHubTrendsIcons
import com.sweak.githubtrends.core.designsystem.theme.GitHubTrendsTheme
import com.sweak.githubtrends.core.designsystem.theme.space
import com.sweak.githubtrends.core.ui.components.ErrorState
import com.sweak.githubtrends.core.ui.util.LanguageColors
import com.sweak.githubtrends.core.ui.util.UiState
import com.sweak.githubtrends.features.repository_details.components.StatCard
import com.sweak.githubtrends.features.repository_details.model.RepositoryDetailsWrapper
import com.sweak.githubtrends.features.repository_details.util.getFormattedDate

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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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
                                Row(
                                    modifier = Modifier
                                        .padding(bottom = MaterialTheme.space.mediumLarge)
                                ) {
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

                            FlowRow(
                                horizontalArrangement =
                                Arrangement.spacedBy(MaterialTheme.space.medium),
                                verticalArrangement =
                                Arrangement.spacedBy(MaterialTheme.space.medium),
                                modifier = Modifier
                                    .padding(bottom = MaterialTheme.space.mediumLarge)
                                    .align(Alignment.CenterHorizontally)
                            ) {
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
                                    value = repositoryDetailsUiState.data.totalStars.toString(),
                                    modifier = Modifier.weight(1f)
                                )

                                if (repositoryDetailsUiState.data.language != null) {
                                    StatCard(
                                        title = stringResource(R.string.language),
                                        icon = {
                                            LanguageColors[repositoryDetailsUiState.data.language]?.let {
                                                Box(
                                                    modifier = Modifier
                                                        .size(size = MaterialTheme.space.large)
                                                        .clip(shape = CircleShape)
                                                        .background(color = it)
                                                )
                                            }
                                        },
                                        value = repositoryDetailsUiState.data.language,
                                        modifier = Modifier.weight(1f)
                                    )
                                }

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
                                    value = repositoryDetailsUiState.data.forks.toString(),
                                    modifier = Modifier.weight(1f)
                                )

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
                                    value = repositoryDetailsUiState.data.watchers.toString(),
                                    modifier = Modifier.weight(1f)
                                )

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
                                    value = repositoryDetailsUiState.data.openIssues.toString(),
                                    modifier = Modifier.weight(1f)
                                )

                                if (repositoryDetailsUiState.data.license != null) {
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
                                        value = repositoryDetailsUiState.data.license,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }

                            Button(
                                onClick = {
                                    onEvent(
                                        RepositoryDetailsScreenUserEvent.OpenGitHubClicked(
                                            repositoryUrl = repositoryDetailsUiState.data.url
                                        )
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    horizontalArrangement =
                                    Arrangement.spacedBy(MaterialTheme.space.smallMedium),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = GitHubTrendsIcons.GitHub,
                                        contentDescription =
                                        stringResource(R.string.content_description_github),
                                        modifier = Modifier
                                            .size(size = MaterialTheme.space.large)
                                    )

                                    Text(
                                        text = stringResource(R.string.see_on_github),
                                        style = MaterialTheme.typography.titleMedium
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
                        usernameAvatarUrl = "https://avatars.githubusercontent.com/u/70141120?v=4",
                        createdAt = 1647771066000,
                        updatedAt = 1740168725000,
                        description = "QRAlarm is an Android alarm clock application that lets the user turn off alarms by scanning the QR Code.",
                        totalStars = 177,
                        language = "Kotlin",
                        forks = 14,
                        watchers = 7,
                        openIssues = 7,
                        license = "GPL-3.0",
                        url = "https://github.com/sweakpl/qralarm-android"
                    )
                )
            ),
            onEvent = { /* no-op */ }
        )
    }
}