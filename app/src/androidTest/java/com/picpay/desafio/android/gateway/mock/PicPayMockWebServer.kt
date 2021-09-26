package com.picpay.desafio.android.gateway.mock

import okhttp3.mockwebserver.MockResponse

object PicPayMockWebServer {

    val successUserResponse by lazy {
        val body =
            "[{\"id\":1001,\"name\":\"Eduardo Santos\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@eduardo.santos\"}]"

        MockResponse()
            .setResponseCode(200)
            .setBody(body)
    }

    val errorUserResponse by lazy { MockResponse().setResponseCode(404) }

    const val MOCK_SERVER_PORT = 8080
}