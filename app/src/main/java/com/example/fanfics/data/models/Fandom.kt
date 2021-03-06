package com.example.fanfics.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fandom(
        var id: Long,
        var name: String?
): Parcelable