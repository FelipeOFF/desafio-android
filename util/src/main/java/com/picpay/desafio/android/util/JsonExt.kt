package com.picpay.desafio.android.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.io.Reader

val gson: Gson by lazy {
    GsonBuilder()
        .disableHtmlEscaping()
        .create()
}

@Throws
inline fun <reified T : Any> String.fromJson(): T =
    checkNotNull(gson.fromJson(this, object : TypeToken<T>() {}.type))

@Throws
inline fun <reified T : Any> Reader.fromJson(): T =
    checkNotNull(gson.fromJson(this, object : TypeToken<T>() {}.type))

inline fun <reified T : Any> String.fromJsonOrNull(): T? =
    try {
        fromJson()
    } catch (ex: JsonParseException) {
        Timber.i(this)
        Timber.e(ex)
        null
    } catch (ex: JsonSyntaxException) {
        Timber.i(this)
        Timber.e(ex)
        null
    } catch (ex: IllegalStateException) {
        Timber.i(this)
        Timber.e(ex)
        null
    } catch (ex: NullPointerException) {
        Timber.i(this)
        Timber.e(ex)
        null
    } catch (ex: ClassCastException) {
        Timber.i(this)
        Timber.e(ex)
        null
    }

inline fun <reified T : Any> Reader.fromJsonOrNull(): T? =
    try {
        fromJson()
    } catch (ex: JsonParseException) {
        Timber.e(ex)
        null
    } catch (ex: JsonSyntaxException) {
        Timber.e(ex)
        null
    } catch (ex: IllegalStateException) {
        Timber.e(ex)
        null
    } catch (ex: NullPointerException) {
        Timber.e(ex)
        null
    } catch (ex: ClassCastException) {
        Timber.e(ex)
        null
    }