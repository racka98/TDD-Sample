package com.example.tdd.data.local.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

object DataStoreHelpers {

    suspend fun <T> writePreference(
        dataStore: DataStore<Preferences>,
        preferenceKey: Preferences.Key<T>,
        value: T
    ) = dataStore.edit { preferences ->
        preferences[preferenceKey] = value
    }

    fun <T> readPreference(
        dataStore: DataStore<Preferences>,
        preferenceKey: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("DataStore", exception.message.toString())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[preferenceKey] ?: defaultValue
        }
}
