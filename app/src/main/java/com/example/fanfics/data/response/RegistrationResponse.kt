package com.example.fanfics.data.response

import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
    @SerializedName("username")
    var username: String,

    @SerializedName("userId")
    var userId: Number
)
