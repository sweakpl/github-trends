package com.sweak.githubtrends.core.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GitHubTrendsPreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun setUiTheme(uiTheme: String) {
        dataStore.edit { preferences ->
            preferences[UI_THEME] = uiTheme
        }
    }

    fun getUiThemeFlow(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[UI_THEME]
        }
    }

    private companion object {
        val UI_THEME = stringPreferencesKey("uiTheme")
    }
}