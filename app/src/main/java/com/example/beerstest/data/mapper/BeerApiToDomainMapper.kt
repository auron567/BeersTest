package com.example.beerstest.data.mapper

import com.example.beerstest.core.mapper.Mapper
import com.example.beerstest.data.network.model.BeerResponse
import com.example.beerstest.domain.model.BeerEntity
import javax.inject.Inject

class BeerApiToDomainMapper @Inject constructor() : Mapper<BeerResponse, BeerEntity>() {

    override fun mapper(input: BeerResponse) = BeerEntity(
        id = input.id,
        name = input.name,
        slogan = input.tagline,
        description = input.description,
        imageUrl = input.imageUrl.orEmpty(),
        productionYear = input.firstBrewed.takeLast(YEAR_LENGTH)
    )
}

private const val YEAR_LENGTH = 4
