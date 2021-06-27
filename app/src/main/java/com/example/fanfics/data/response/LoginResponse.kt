package com.example.fanfics.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
        @SerializedName("id")
        var id: Long,

        @SerializedName("email")
        var email: String,

        @SerializedName("accessToken")
        var accessToken: String
)
