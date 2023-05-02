package com.example.lets_shop

import android.app.Application
import com.example.lets_shop.di.ApplicationComponent
import com.example.lets_shop.di.DaggerApplicationComponent

class ShopApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}