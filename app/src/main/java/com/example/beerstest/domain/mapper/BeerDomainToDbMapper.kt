package com.example.beerstest.domain.mapper

import com.example.beerstest.core.mapper.Mapper
import com.example.beerstest.data.database.model.BeerDb
import com.example.beerstest.domain.model.BeerEntity
import javax.inject.Inject

class BeerDomainToDbMapper @Inject constructor() : Mapper<BeerEntity, BeerDb>() {

    override fun mapper(input: BeerEntity) = BeerDb(
        id = input.id,
        name = input.name
    )
}
