package com.example.atomyze_test

import android.app.Application

class MainApplication : Application() {

    val mainComponent: MainComponent by lazy {
        DaggerMainComponent.factory().create(applicationContext)
    }

}