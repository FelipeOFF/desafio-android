package com.picpay.desafio.android

import com.picpay.desafio.android.gateway.service.PicPayService
import com.picpay.desafio.android.users.res.User

class ExampleService(
    private val service: PicPayService
) {

    fun example(): List<User> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}