package com.example.beerstest.domain.usecase

import com.example.beerstest.domain.repository.BeerRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class IsFavoriteUseCase @Inject constructor(
    private val beerRepository: BeerRepository
) {
    operator fun invoke(id: Int): Flow<Boolean> = beerRepository.isFavorite(id)
}
