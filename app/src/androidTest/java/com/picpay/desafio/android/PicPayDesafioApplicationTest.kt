package com.picpay.desafio.android

import com.picpay.desafio.android.di.gateway.gatewayModuleMock

class PicPayDesafioApplicationTest : PicPayDesafioApplication() {
    override fun onCreate() {
        super.onCreate()
        koinApplication.modules(gatewayModuleMock)
    }
}