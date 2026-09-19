package com.sentinelbank.core.storage.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Wraps an [EncryptedSharedPreferences] backed by an AES256-GCM [MasterKey] for session/token
 * and other sensitive key-value storage. For non-sensitive app preferences, use
 * [com.sentinelbank.core.storage.prefs.AppPreferencesDataStore] instead.
 */
class SecurePrefsManager(context: Context, fileName: String = DEFAULT_FILE_NAME) {

    private val masterKey = MasterKey.Builder(context.applicationContext)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context.applicationContext,
        fileName,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
    )

    fun putString(key: String, value: String?) {
        prefs.edit().putString(key, value).apply()
    }

    fun getString(key: String, default: String? = null): String? =
        prefs.getString(key, default)

    fun putBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, default: Boolean = false): Boolean =
        prefs.getBoolean(key, default)

    fun putLong(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }

    fun getLong(key: String, default: Long = 0L): Long =
        prefs.getLong(key, default)

    fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }

    fun clear() {
        prefs.edit().clear().apply()
    }

    fun saveSessionToken(token: String) = putString(KEY_SESSION_TOKEN, token)

    fun getSessionToken(): String? = getString(KEY_SESSION_TOKEN)

    fun clearSession() {
        remove(KEY_SESSION_TOKEN)
        remove(KEY_SESSION_EXPIRES_AT)
    }

    fun saveSessionExpiresAt(epochMillis: Long) = putLong(KEY_SESSION_EXPIRES_AT, epochMillis)

    fun getSessionExpiresAt(): Long? =
        if (prefs.contains(KEY_SESSION_EXPIRES_AT)) getLong(KEY_SESSION_EXPIRES_AT) else null

    companion object {
        private const val DEFAULT_FILE_NAME = "sentinel_secure_prefs"
        private const val KEY_SESSION_TOKEN = "session_token"
        private const val KEY_SESSION_EXPIRES_AT = "session_expires_at"
    }
}
