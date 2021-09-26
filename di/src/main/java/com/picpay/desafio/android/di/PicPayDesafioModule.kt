package com.picpay.desafio.android.di

import com.picpay.desafio.android.di.gateway.gatewayModule
import org.koin.core.module.Module
import org.koin.dsl.module

val picPayDesafioModule = module {
    // When have a global dependency
}

val picPayDesafioModules: List<Module> = listOf(
    picPayDesafioModule,
    gatewayModule
)