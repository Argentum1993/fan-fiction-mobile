package com.example.fanfics.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fanfic(
        val serialVersionUID: Long = 1L,
        var id: Long,
        var title: String?,
        var author: String?,
        var img: String?,
        var description: String?,
        var publicationDate: String?,
        var tags: Array<Tag>,
        var fandom: Fandom,
        var rating: Int
        ): Parcelable