package com.example.assignment.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// Singleton DataStore instance
private val Context.userPrefsDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferences(context: Context) {
    private val dataStore = context.userPrefsDataStore

    companion object {
        // Keys for preferences
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val USER_PHONE_KEY = stringPreferencesKey("user_phone")
        private val USER_PROFILE_PIC_KEY = stringPreferencesKey("user_profile_pic")

        // Default values
        private const val DEFAULT_NAME = "John Doe"
        private const val DEFAULT_EMAIL = "john.doe@example.com"
        private const val DEFAULT_PHONE = "+123 456 7890"
        private const val DEFAULT_PROFILE_PIC = ""
    }

    // Fetch user data with error handling
    val getUserName: Flow<String> = dataStore.data
        .catch { exception -> if (exception is IOException) emit(emptyPreferences()) }
        .map { it[USER_NAME_KEY] ?: DEFAULT_NAME }

    val getUserEmail: Flow<String> = dataStore.data
        .catch { exception -> if (exception is IOException) emit(emptyPreferences()) }
        .map { it[USER_EMAIL_KEY] ?: DEFAULT_EMAIL }

    val getUserPhone: Flow<String> = dataStore.data
        .catch { exception -> if (exception is IOException) emit(emptyPreferences()) }
        .map { it[USER_PHONE_KEY] ?: DEFAULT_PHONE }

    val getUserProfilePic: Flow<String> = dataStore.data
        .catch { exception -> if (exception is IOException) emit(emptyPreferences()) }
        .map { it[USER_PROFILE_PIC_KEY] ?: DEFAULT_PROFILE_PIC }

    // Save functions with exception handling
    suspend fun saveUserName(name: String) {
        dataStore.edit { it[USER_NAME_KEY] = name }
    }

    suspend fun saveUserEmail(email: String) {
        dataStore.edit { it[USER_EMAIL_KEY] = email }
    }

    suspend fun saveUserPhone(phone: String) {
        dataStore.edit { it[USER_PHONE_KEY] = phone }
    }

    suspend fun saveUserProfilePic(uri: String) {
        dataStore.edit { it[USER_PROFILE_PIC_KEY] = uri }
    }
}
