package com.picpay.desafio.android.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(Const.Store.FILE_STORE)

inline fun <reified T : Any> Context.dataStoreRead(key: String): Flow<T?> {
    val preferenceKey = stringPreferencesKey(key)
    return dataStore.data.map {preference ->
        preference[preferenceKey]?.fromJsonOrNull()
    }
}

suspend inline fun <reified T : Any> Context.dataStoreWrite(key: String, value: T) {
    val preferenceKey = stringPreferencesKey(key)
    dataStore.edit { preference ->
        val json = value.toJsonOrNull()
        if (json != null) {
            preference[preferenceKey] = json
        }
    }
}