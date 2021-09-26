package com.picpay.desafio.android.di

import com.picpay.desafio.android.di.domain.domainModule
import com.picpay.desafio.android.di.gateway.gatewayModule
import com.picpay.desafio.android.di.repository.repositoryModule
import org.koin.core.module.Module
import org.koin.dsl.module

val picPayDesafioModule = module {
    // When have a global dependency
}

val picPayDesafioModules: List<Module> = listOf(
    picPayDesafioModule,
    gatewayModule,
    repositoryModule,
    domainModule
)