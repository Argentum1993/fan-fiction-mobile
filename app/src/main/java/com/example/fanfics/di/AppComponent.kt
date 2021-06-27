package com.example.fanfics.di

import com.example.fanfics.data.ApiService
import com.example.fanfics.data.AuthInterceptor
import com.example.fanfics.ui.auth.LoginActivity
import com.example.fanfics.ui.auth.RegistrationActivity
import com.example.fanfics.ui.user.fanfics.UserFanficRepository
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RemoteModule::class])
@Singleton
interface AppComponent {

    fun getUserFanficRepository(): UserFanficRepository
    fun getAuthInterceptor(): AuthInterceptor
    fun getApiService(): ApiService
}