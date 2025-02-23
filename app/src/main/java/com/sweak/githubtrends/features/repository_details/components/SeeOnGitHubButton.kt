package com.sweak.githubtrends.features.repository_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.designsystem.icon.GitHubTrendsIcons
import com.sweak.githubtrends.core.designsystem.theme.space

@Composable
fun SeeOnGitHubButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
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