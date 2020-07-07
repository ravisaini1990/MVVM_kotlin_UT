package com.example

import android.app.Application
import com.example.mvvm_kotlin_ut.di.DaggerNetworkComponent
import com.example.mvvm_kotlin_ut.di.NetworkModule

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDaggerComponent()
    }

    private fun initDaggerComponent() {
        DaggerNetworkComponent.builder().networkModule(NetworkModule()).build()
    }
}