package com.example.sunsafe

import android.app.Application
import com.example.sunsafe.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SunSafeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SunSafeApplication)
            modules(appModule)
        }
    }
}
