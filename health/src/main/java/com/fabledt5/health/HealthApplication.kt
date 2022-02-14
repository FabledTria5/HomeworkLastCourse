package com.fabledt5.health

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HealthApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(applicationContext)
    }

}