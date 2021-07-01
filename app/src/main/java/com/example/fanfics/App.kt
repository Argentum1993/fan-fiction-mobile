package com.example.fanfics;

import android.app.Application;
import androidx.multidex.MultiDexApplication
import com.example.fanfics.data.models.User
import com.example.fanfics.di.AppComponent
import com.example.fanfics.di.AuthComponent
import com.example.fanfics.di.DaggerAppComponent
import com.example.fanfics.di.DaggerAuthComponent

class App: MultiDexApplication() {
    companion object {
        val appComponent: AppComponent = DaggerAppComponent.create()
        lateinit var instance: App
        var token: String? = null
        private var user: User? = null

        fun setUser(user: User?){
            this.user = user
        }

        fun getUser(): User?{
            return user
        }
    }

    private var authComponent: AuthComponent? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    // Singleton
    fun getAuthComponent(): AuthComponent{
        if (authComponent == null){
            // start lifecycle of AuthComponent
            authComponent = DaggerAuthComponent.create()
        }
        return authComponent!!
    }

    fun clearAuthComponent(){
        // end lifecycle of AuthComponent
        authComponent = null
    }


}
