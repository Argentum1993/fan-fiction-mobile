package com.example.fanfics.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Chapter(
    val id: Long,
    val name: String,
    val img: String,
    val text: String
): Parcelable
