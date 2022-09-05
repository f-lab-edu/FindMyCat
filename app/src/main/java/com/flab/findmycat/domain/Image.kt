package com.flab.findmycat.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    val height: Int?,
    val id: String?,
    val width: Int?,
    val url: String?
)
