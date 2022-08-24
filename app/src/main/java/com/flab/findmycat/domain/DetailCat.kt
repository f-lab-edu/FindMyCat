package com.flab.findmycat.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailCat(
    @Json(name = "height")
    val height: Int?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "width")
    val width: Int?
)