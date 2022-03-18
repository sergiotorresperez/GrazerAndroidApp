package com.sergiotorres.grazer.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GrazerApplication : Application() {
    companion object {
        lateinit var instance: GrazerApplication
    }

    init {
        instance = this
    }
}
