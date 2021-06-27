package com.example.fanfics.data

import com.example.fanfics.data.models.Fanfic
import com.example.fanfics.data.requests.LoginRequest
import com.example.fanfics.data.requests.RegistrationRequest
import com.example.fanfics.data.response.LoginResponse
import com.example.fanfics.data.response.RegistrationResponse
import com.example.fanfics.utils.Constants
import retrofit2.Call
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface ApiService {

    @POST(Constants.LOGIN_URL)
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST(Constants.REGISTR_URL)
    fun registration(@Body request: RegistrationRequest): Call<RegistrationResponse>

    @GET("/{id}/fanfic")
    suspend fun getUserFanfic(
        @Path("id") id: Long,
        @Query("pageNumber") page: Int,
        @Query("pageSize") size: Int): List<Fanfic>

}