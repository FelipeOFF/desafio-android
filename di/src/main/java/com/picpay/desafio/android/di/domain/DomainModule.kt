package com.picpay.desafio.android.di.domain

import com.picpay.desafio.android.domain.user.GetUserUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetUserUseCase(get()) }
}