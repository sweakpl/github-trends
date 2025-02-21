package com.sweak.githubtrends.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.sweak.githubtrends.R
import com.sweak.githubtrends.core.designsystem.icon.GitHubTrendsIcons
import com.sweak.githubtrends.core.designsystem.theme.GitHubTrendsTheme
import com.sweak.githubtrends.core.designsystem.theme.space

@Composable
fun ErrorState(
    errorMessage: String,
    onTryAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            imageVector = GitHubTrendsIcons.Error,
            contentDescription = stringResource(R.string.content_description_error),
            modifier = Modifier.size(MaterialTheme.space.xLarge)
        )

        Text(
            text = errorMessage,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = MaterialTheme.space.medium)
        )

        Button(onClick = onTryAgain) {
            Text(text = stringResource(R.string.try_again))
        }
    }
}

@Preview
@Composable
private fun ErrorStatePreview() {
    GitHubTrendsTheme {
        ErrorState(
            errorMessage = "There was a connection problem. Check your internet and try again.",
            onTryAgain = { /* no-op */ },
            modifier = Modifier.fillMaxWidth()
        )
    }
}