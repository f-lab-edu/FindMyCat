package com.flab.findmycat.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cat(
    val id: String,
    val name: String = "name",
    val image: Image?
)
