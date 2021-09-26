package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.picPayDesafioModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

open class PicPayDesafioApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        koinApplication
    }

    protected val koinApplication: KoinApplication by lazy {
        startKoin {
            androidContext(this@PicPayDesafioApplication)
            androidFileProperties()
            koin.loadModules(picPayDesafioModules)
        }
    }
}