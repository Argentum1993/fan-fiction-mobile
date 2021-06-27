package com.example.fanfics.data.models

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("id")
    var id: Long,

    @SerializedName("firsName")
    var firsName: String,

    @SerializedName("lastName")
    var lastName: String,

    @SerializedName("email")
    var email: String
)
