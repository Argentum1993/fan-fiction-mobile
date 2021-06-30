package com.example.fanfics.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(
        val serialVersionUID: Long = 1L,
        var id: Long,
        var name: String?
        ): Parcelable