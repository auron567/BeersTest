package com.example.beerstest.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BeerResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "tagline") val tagline: String,
    @Json(name = "description") val description: String,
    @Json(name = "image_url") val imageUrl: String?,
    @Json(name = "first_brewed") val firstBrewed: String
)
