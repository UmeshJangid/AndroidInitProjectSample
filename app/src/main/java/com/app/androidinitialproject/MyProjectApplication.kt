package com.app.androidinitialproject

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication


class MyProjectApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        AppManager.setInstance(AppManager(applicationContext))
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}
