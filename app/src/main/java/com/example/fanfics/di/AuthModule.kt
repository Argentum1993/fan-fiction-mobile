package com.example.fanfics.di

import com.example.fanfics.data.AuthService
import com.example.fanfics.di.scope.AuthScope
import com.example.fanfics.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AuthModule {
    @Provides
    @AuthScope
    fun createAuthService(): AuthService {
        return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthService::class.java)
    }
}