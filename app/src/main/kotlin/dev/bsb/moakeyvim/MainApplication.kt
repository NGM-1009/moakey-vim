package dev.bsb.moakeyvim

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import dev.bsb.moakeyvim.module.configModule

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(configModule))
        }
    }

}