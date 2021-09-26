package com.picpay.desafio.android.di.gateway

import com.picpay.desafio.android.gateway.client.PicPayClient
import org.koin.dsl.module

val gatewayModule = module {
    single { PicPayClient() }
    single { get<PicPayClient>().service }
}