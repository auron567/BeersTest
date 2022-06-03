package com.example.beerstest.domain.repository

import com.example.beerstest.domain.model.BeerEntity

interface BeerRepository {

    suspend fun getBeers(isStart: Boolean, filterName: String?): List<BeerEntity>
}
