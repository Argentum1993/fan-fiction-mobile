package com.example.fanfics.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(

    @SerializedName("id")
    var id: Long,

    @SerializedName("firsName")
    var firsName: String,

    @SerializedName("lastName")
    var lastName: String,

    @SerializedName("email")
    var email: String

    ): Parcelable
