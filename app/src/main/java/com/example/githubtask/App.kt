package com.example.githubtask

import android.app.Application
import com.example.githubtask.di.appModule
import com.example.githubtask.di.repoModule
import com.example.githubtask.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}