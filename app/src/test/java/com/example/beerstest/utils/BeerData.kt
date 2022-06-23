package com.example.beerstest.utils

import com.example.beerstest.data.database.model.BeerDb
import com.example.beerstest.data.network.model.BeerResponse
import com.example.beerstest.domain.model.BeerEntity

fun createBeerResponse(
    id: Int = ID,
    name: String = NAME,
    tagline: String = SLOGAN,
    description: String = DESCRIPTION,
    imageUrl: String = IMAGE_URL,
    firstBrewed: String = FIRST_BREWED
) = BeerResponse(
    id = id,
    name = name,
    tagline = tagline,
    description = description,
    imageUrl = imageUrl,
    firstBrewed = firstBrewed
)

fun createBeerEntity(
    id: Int = ID,
    name: String = NAME,
    slogan: String = SLOGAN,
    description: String = DESCRIPTION,
    imageUrl: String = IMAGE_URL,
    productionYear: String = PRODUCTION_YEAR
) = BeerEntity(
    id = id,
    name = name,
    slogan = slogan,
    description = description,
    imageUrl = imageUrl,
    productionYear = productionYear
)

fun createBeerDb(
    id: Int = ID,
    name: String = NAME
) = BeerDb(
    id = id,
    name = name
)

private const val ID = 1
private const val NAME = "Buzz"
private const val SLOGAN = "A Real Bitter Experience."
private const val FIRST_BREWED = "09/2007"
private const val PRODUCTION_YEAR = "2007"
private const val IMAGE_URL = "https://images.punkapi.com/v2/keg.png"
private const val DESCRIPTION = "A light, crisp and bitter IPA brewed with English and American hops."
