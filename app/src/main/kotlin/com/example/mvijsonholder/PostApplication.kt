package com.example.mvijsonholder

import android.app.Application
import com.example.mvijsonholder.di.appModule
import org.koin.core.context.startKoin

class PostApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule)
        }
    }
}
