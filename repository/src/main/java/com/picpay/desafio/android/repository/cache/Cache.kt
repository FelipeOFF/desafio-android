package com.picpay.desafio.android.repository.cache

interface Cache<T> {
    suspend fun save(key: String, value: T)
    suspend fun get(key: String): T?
    suspend fun clear(key: String)
    suspend fun clearAll()
}