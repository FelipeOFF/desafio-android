package com.picpay.desafio.android.util

inline fun <reified T : Any> List<Any>.safeHeritage(): List<T> =
    mapNotNull {
        it as? T
    }