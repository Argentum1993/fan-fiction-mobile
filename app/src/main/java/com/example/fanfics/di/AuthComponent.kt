package com.example.fanfics.di

import com.example.fanfics.di.scope.AuthScope
import com.example.fanfics.ui.auth.LoginActivity
import com.example.fanfics.ui.auth.RegistrationActivity
import dagger.Component


@Component(modules = [AuthModule::class])
@AuthScope
interface AuthComponent {
    fun inject(activity: LoginActivity)
    fun inject(activity: RegistrationActivity)
}