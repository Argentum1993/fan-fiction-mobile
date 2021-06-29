package com.example.fanfics.data

import com.example.fanfics.data.models.Fanfic
import com.example.fanfics.data.models.User
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

    @GET("${Constants.USERS_URL}/{id}/fanfic")
    suspend fun getUserFanfic(
        @Path("id") id: Long,
        @Query("pageNumber") page: Int,
        @Query("pageSize") size: Int): List<Fanfic>

    @POST("${Constants.USERS_URL}/{id}")
    suspend fun getUser(@Path("id") id: Long): User

    @POST("${Constants.USERS_URL}/{id}/fanfic/recommended")
    suspend fun getRecommendedFanfics(
        @Path("id") id: Long,
        @Query("maxItem") maxItem: Int): List<Fanfic>

    @POST("${Constants.FANFiC_URL}/random")
    suspend fun getRandomFanfics(@Query("maxItem") maxItem: Int): List<Fanfic>
}