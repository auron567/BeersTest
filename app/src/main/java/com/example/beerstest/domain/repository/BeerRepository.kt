package com.example.beerstest.domain.repository

import com.example.beerstest.domain.model.BeerEntity
import kotlinx.coroutines.flow.Flow

interface BeerRepository {

    suspend fun getBeers(isStart: Boolean, filterName: String?): List<BeerEntity>

    suspend fun saveFavorite(beerEntity: BeerEntity)

    suspend fun removeFavorite(id: Int)

    fun isFavorite(id: Int): Flow<Boolean>
}
