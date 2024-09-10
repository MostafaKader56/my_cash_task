package com.bb4first.mycashtask

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyCashTaskApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    companion object {
        lateinit var instance: MyCashTaskApplication
            private set
    }

}