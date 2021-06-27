package com.example.fanfics.data

import com.example.fanfics.data.requests.LoginRequest
import com.example.fanfics.data.requests.RegistrationRequest
import com.example.fanfics.data.response.LoginResponse
import com.example.fanfics.data.response.RegistrationResponse
import com.example.fanfics.di.scope.AuthScope
import com.example.fanfics.utils.Constants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

@AuthScope
interface AuthService {

    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse //Call<LoginResponse>

    @POST(Constants.REGISTR_URL)
    suspend fun registration(@Body request: RegistrationRequest): RegistrationResponse //Call<RegistrationResponse>
}