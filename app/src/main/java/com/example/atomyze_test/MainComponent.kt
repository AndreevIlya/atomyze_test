package com.example.atomyze_test

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [MainModule::class]
)
interface MainComponent {

    @Component.Factory
    interface Factory{

        fun create(@BindsInstance context: Context): MainComponent

    }

    fun injectMain(activity: MainActivity)

}