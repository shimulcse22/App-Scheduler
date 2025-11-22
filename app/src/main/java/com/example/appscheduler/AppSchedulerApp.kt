package com.example.appscheduler

import android.app.Application
import com.example.appscheduler._modules.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppSchedulerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        injectModules()
    }
    private fun injectModules() {
        startKoin {
            androidContext(this@AppSchedulerApp)
            modules(koinModules)
        }
    }
}