package com.picpay.desafio.android.di.repository

import com.picpay.desafio.android.repository.user.PicPayUserRepository
import com.picpay.desafio.android.repository.user.PicPayUserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<PicPayUserRepository> { PicPayUserRepositoryImpl(get()) }
}