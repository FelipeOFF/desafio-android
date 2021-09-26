package com.picpay.desafio.android.di.gateway

import com.picpay.desafio.android.gateway.client.MockClient
import org.koin.dsl.module

val gatewayModuleMock = module {
    single(override = true) { MockClient() }
    single(override = true) { get<MockClient>().service }
}