package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.picPayDesafioModules
import com.picpay.desafio.android.tree.ProdTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import timber.log.Timber

open class PicPayDesafioApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupLibraries()
    }

    private fun setupLibraries() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ProdTree())
        }

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