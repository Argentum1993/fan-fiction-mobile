package com.example.fanfics.di

import com.example.fanfics.ui.auth.LoginActivity
import com.example.fanfics.ui.auth.RegistrationActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RemoteModule::class])
@Singleton
interface AppComponent {

    fun inject(activity: LoginActivity)
    fun inject(activity: RegistrationActivity)
}