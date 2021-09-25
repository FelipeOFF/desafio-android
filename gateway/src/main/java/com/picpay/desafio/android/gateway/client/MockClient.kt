package com.picpay.desafio.android.gateway.client

import com.picpay.desafio.android.gateway.AbstractGateway
import com.picpay.desafio.android.gateway.URLs
import com.picpay.desafio.android.gateway.service.PicPayService
import retrofit2.Retrofit

class MockClient : AbstractGateway() {

    override val urls: URLs = URLs.URL_MOCK

    private val client: Retrofit by getRetrofit()

    val service: PicPayService by lazy {
        client.create(PicPayService::class.java)
    }

}
