package com.example.fanfics.di

import com.example.fanfics.App
import com.example.fanfics.data.ApiService
import com.example.fanfics.data.AuthInterceptor
import com.example.fanfics.data.AuthService
import com.example.fanfics.di.scope.AuthScope
import com.example.fanfics.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    fun createApiService(interceptor: AuthInterceptor): ApiService{
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        interceptor.setAccessToken(App.token)
        return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
    }
}