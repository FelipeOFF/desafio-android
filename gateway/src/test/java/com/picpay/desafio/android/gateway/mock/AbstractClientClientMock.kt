package com.picpay.desafio.android.gateway.mock

import com.picpay.desafio.android.gateway.AbstractClient
import com.picpay.desafio.android.gateway.URLs
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class AbstractClientClientMock(
    override val urls: URLs,
    private val httpClientBuilder: OkHttpClient.Builder,
    private val retrofitClientBuilder: Retrofit.Builder,
    override val enableHttpLogging: Boolean,
    override val httpLoggingInterceptor: HttpLoggingInterceptor
) : AbstractClient() {
    override fun getHttpClientBuilder(): OkHttpClient.Builder =
        httpClientBuilder

    override fun getRetrofitClientBuilder(): Retrofit.Builder =
        retrofitClientBuilder
}