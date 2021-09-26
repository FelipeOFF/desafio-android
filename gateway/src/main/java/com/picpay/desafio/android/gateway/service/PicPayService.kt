package com.picpay.desafio.android.gateway.service

import com.picpay.desafio.android.model.users.res.User
import retrofit2.Call
import retrofit2.http.GET

interface PicPayService {

    @Deprecated("Use function suspend")
    @GET("users")
    fun getUsers(): Call<List<User>>

    @GET("users")
    suspend fun getUsersSuspend(): List<User>
}
