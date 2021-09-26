package com.picpay.desafio.android.gateway.mock

import okhttp3.mockwebserver.MockResponse
import java.net.HttpURLConnection

object PicPayMockWebServer {

    val successUserResponse by lazy {
        val body = """
            [
                {
                    "id": 1001,
                    "name": "Eduardo Santos",
                    "img": "https://randomuser.me/api/portraits/men/9.jpg",
                    "username": "@eduardo.santos"
                }
            ]
        """.trimIndent()

        MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(body)
    }

    val errorUserUnauthorizedResponse by lazy {
        val body = """
            {
                "message": "Acesso negado"
            }
        """.trimIndent()

        MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED)
            .setBody(body)
    }

    val errorUserResponse by lazy { MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND) }

    const val MOCK_SERVER_PORT = 8080
}