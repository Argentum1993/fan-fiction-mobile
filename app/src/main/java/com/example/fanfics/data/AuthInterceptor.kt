package com.example.fanfics.data

import com.example.fanfics.App
import com.example.fanfics.utils.Constants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor() : Interceptor {
    private var accessToken : String? = App.token

    fun setAccessToken(accessToken: String?){
        this.accessToken = accessToken
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        var requestBuilder: Request.Builder = request.newBuilder()
        if (request.header(Constants.AUTH_HEADER) == null){
            if (accessToken == null)
                throw RuntimeException("Access token should be defined for auth apis")
            else
                requestBuilder.addHeader(Constants.AUTH_HEADER, accessToken!!)
        }
        return chain.proceed(requestBuilder.build())
    }
}