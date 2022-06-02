package com.example.beerstest.domain.repository

import com.example.beerstest.domain.model.BeerEntity

interface BeerRepository {

    suspend fun getBeers(): List<BeerEntity>
}
