package com.example.beers

import android.app.Application
import com.example.beers.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BeerApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BeerApplication)
            modules(appModule)
        }
    }

}