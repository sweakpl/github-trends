package com.sweak.githubtrends.core.domain.user

import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    suspend fun setUiThemeMode(uiThemeMode: UiThemeMode)
    val uiThemeModeFlow: Flow<UiThemeMode>
}