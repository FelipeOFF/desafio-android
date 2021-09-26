package com.picpay.desafio.android.repository.user

import com.picpay.desafio.android.model.users.res.User

interface PicPayUserRepository {
    suspend fun getListOfUsers(): List<User>
}
