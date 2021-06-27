package com.example.fanfics.data.models

import java.util.*

data class Fanfic(
    var id: Long,
    var title : String,
    var img : String,
    var description : String,
    var publicationDate: String,
    var tags: Array<Tag>,
    var fandom: Fandom,
    var rating: Int
)
