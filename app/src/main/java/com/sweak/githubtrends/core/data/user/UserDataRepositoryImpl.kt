package com.sweak.githubtrends.core.data.user

import com.sweak.githubtrends.core.domain.user.UiThemeMode
import com.sweak.githubtrends.core.domain.user.UserDataRepository
import com.sweak.githubtrends.core.storage.GitHubTrendsPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val gitHubTrendsPreferencesDataSource: GitHubTrendsPreferencesDataSource
) : UserDataRepository {

    override suspend fun setUiThemeMode(uiThemeMode: UiThemeMode) {
        gitHubTrendsPreferencesDataSource.setUiTheme(uiTheme = uiThemeMode.name)
    }

    override val uiThemeModeFlow: Flow<UiThemeMode>
        get() = gitHubTrendsPreferencesDataSource.getUiThemeFlow().map {
            if (it == null) {
                UiThemeMode.LIGHT
            } else {
                try {
                    UiThemeMode.valueOf(it)
                } catch (illegalArgumentException: IllegalArgumentException) {
                    UiThemeMode.LIGHT
                }
            }
        }
}