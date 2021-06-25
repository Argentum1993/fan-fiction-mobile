package com.example.fanfics;

import android.app.Application;
import com.example.fanfics.di.DaggerAppComponent

class App: Application() {

    companion object {
        val appComponent = DaggerAppComponent.create()
    }
}
