package com.picpay.desafio.android.gateway.service

import com.picpay.desafio.android.users.res.User
import retrofit2.Call
import retrofit2.http.GET

interface PicPayService {
    @GET("users")
    fun getUsers(): Call<List<User>>
}
