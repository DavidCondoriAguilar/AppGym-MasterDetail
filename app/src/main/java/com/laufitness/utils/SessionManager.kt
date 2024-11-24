package com.laufitness.utils


import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Crear instancia de DataStore
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class SessionManager(private val context: Context) {
    companion object {
        private val KEY_REMEMBER_ME = booleanPreferencesKey("remember_me")
        private val KEY_EMAIL = stringPreferencesKey("user_email")
    }

    // Guardar preferencia de sesión
    suspend fun saveSession(rememberMe: Boolean, email: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_REMEMBER_ME] = rememberMe
            if (rememberMe) {
                preferences[KEY_EMAIL] = email
            } else {
                preferences.remove(KEY_EMAIL)
            }
        }
    }

    // Leer preferencia de sesión
    val isRemembered: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[KEY_REMEMBER_ME] ?: false
    }

    val rememberedEmail: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[KEY_EMAIL]
    }
}
