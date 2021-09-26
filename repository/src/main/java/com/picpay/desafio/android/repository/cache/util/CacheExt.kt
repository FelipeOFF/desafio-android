package com.picpay.desafio.android.repository.cache.util

import com.picpay.desafio.android.repository.cache.CoCache
import com.picpay.desafio.android.repository.cache.store.HawkCacheStore

suspend fun <T> String.getFromHawkCache(forceLoad: Boolean? = false, asyncValue: suspend () -> T): T =
    CoCache<T>(HawkCacheStore()).get(this, forceLoad, asyncValue)

suspend fun <T> String.invalidateHawkCache() =
    CoCache<T>(HawkCacheStore()).clearThisCache(this)

suspend fun clearAllHawkCache() =
    CoCache<Any>(HawkCacheStore()).clearAllCache()