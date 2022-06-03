package com.example.beerstest.domain.usecase

import com.example.beerstest.domain.model.BeerEntity
import com.example.beerstest.domain.repository.BeerRepository
import javax.inject.Inject

class GetBeersUseCase @Inject constructor(
    private val beerRepository: BeerRepository
) {
    suspend operator fun invoke(isStart: Boolean, filterName: String?): List<BeerEntity> =
        beerRepository.getBeers(isStart, filterName)
}
