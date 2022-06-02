package com.example.beerstest.data.network.mapper

import com.example.beerstest.core.mapper.ApiToDomainMapper
import com.example.beerstest.data.network.model.BeerResponse
import com.example.beerstest.domain.model.BeerEntity
import javax.inject.Inject

class BeerApiToDomainMapper @Inject constructor() : ApiToDomainMapper<BeerResponse, BeerEntity>() {

    override fun map(input: BeerResponse) = BeerEntity(
        id = input.id,
        name = input.name,
        slogan = input.tagline,
        description = input.description,
        imageUrl = input.imageUrl,
        productionYear = input.firstBrewed.takeLast(YEAR_LENGTH)
    )
}

private const val YEAR_LENGTH = 4
