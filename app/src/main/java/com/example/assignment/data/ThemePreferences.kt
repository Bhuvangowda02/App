package com.example.assignment.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "theme_prefs")

class ThemePreferences(context: Context) {
    private val dataStore = context.dataStore
    private val themeKey = booleanPreferencesKey("is_dark_theme")

    val themeFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[themeKey] ?: false
    }

    suspend fun saveTheme(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[themeKey] = isDark
        }
    }
}
