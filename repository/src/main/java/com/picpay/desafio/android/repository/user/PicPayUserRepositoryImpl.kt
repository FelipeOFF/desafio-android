package com.picpay.desafio.android.repository.user

import com.picpay.desafio.android.gateway.service.PicPayService
import com.picpay.desafio.android.model.users.res.User

class PicPayUserRepositoryImpl(
    private val picPayService: PicPayService
) : PicPayUserRepository {
    override suspend fun getListOfUsers(): List<User> =
        picPayService.getUsersSuspend()
}
