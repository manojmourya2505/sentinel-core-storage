package com.sentinelbank.core.storage.prefs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "sentinel_app_prefs")

/**
 * Plain (non-encrypted) Jetpack DataStore Preferences wrapper for non-sensitive app settings.
 * For anything sensitive (tokens, credentials), use [SecurePrefsManager] instead.
 */
class AppPreferencesDataStore(private val context: Context) {

    private object Keys {
        val ONBOARDING_COMPLETE = booleanPreferencesKey("onboarding_complete")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val SESSION_TIMEOUT_MINUTES = intPreferencesKey("session_timeout_minutes")
    }

    val isOnboardingComplete = context.dataStore.data.map { prefs ->
        prefs[Keys.ONBOARDING_COMPLETE] ?: false
    }

    suspend fun setOnboardingComplete(complete: Boolean) {
        context.dataStore.edit { prefs -> prefs[Keys.ONBOARDING_COMPLETE] = complete }
    }

    val themeMode = context.dataStore.data.map { prefs ->
        prefs[Keys.THEME_MODE] ?: DEFAULT_THEME_MODE
    }

    suspend fun setThemeMode(mode: String) {
        context.dataStore.edit { prefs -> prefs[Keys.THEME_MODE] = mode }
    }

    val sessionTimeoutMinutes = context.dataStore.data.map { prefs ->
        prefs[Keys.SESSION_TIMEOUT_MINUTES] ?: DEFAULT_SESSION_TIMEOUT_MINUTES
    }

    suspend fun setSessionTimeoutMinutes(minutes: Int) {
        context.dataStore.edit { prefs -> prefs[Keys.SESSION_TIMEOUT_MINUTES] = minutes }
    }

    companion object {
        const val DEFAULT_THEME_MODE = "SYSTEM"
        const val DEFAULT_SESSION_TIMEOUT_MINUTES = 5
    }
}
