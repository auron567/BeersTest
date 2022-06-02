package com.example.beerstest.data.network.model

import com.google.gson.annotations.SerializedName

data class BeerResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("description") val description: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("first_brewed") val firstBrewed: String
)
