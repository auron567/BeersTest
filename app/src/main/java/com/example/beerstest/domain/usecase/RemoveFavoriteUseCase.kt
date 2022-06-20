package com.example.beerstest.domain.usecase

import com.example.beerstest.domain.repository.BeerRepository
import javax.inject.Inject

class RemoveFavoriteUseCase @Inject constructor(
    private val beerRepository: BeerRepository
) {
    suspend operator fun invoke(id: Int) {
        beerRepository.removeFavorite(id)
    }
}
