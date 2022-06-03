package com.example.beerstest.data.repository

import com.example.beerstest.core.exceptions.toNetworkError
import com.example.beerstest.data.network.mapper.BeerApiToDomainMapper
import com.example.beerstest.data.network.model.BeerResponse
import com.example.beerstest.data.network.service.BeerService
import com.example.beerstest.domain.model.BeerEntity
import com.example.beerstest.domain.repository.BeerRepository
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(
    private val beerService: BeerService,
    private val beerMapper: BeerApiToDomainMapper
) : BeerRepository {

    private var cachedData: MutableList<BeerEntity> = mutableListOf()

    private var pageCount: Int = FIRST_PAGE

    override suspend fun getBeers(isStart: Boolean, filterName: String?): List<BeerEntity> {
        // Clear current list and reset first page
        if (isStart) {
            cachedData.clear()
            pageCount = FIRST_PAGE
        }

        return beerService.getBeers(
            beerName = filterName,
            page = pageCount
        ).let { response ->
            if (response.isSuccessful) {
                handleSuccessfulResponse(response.body())
            } else {
                throw response.code().toNetworkError()
            }
        }
    }

    private fun handleSuccessfulResponse(beersApi: List<BeerResponse>?): List<BeerEntity> {
        // Set next page
        pageCount++

        // Get domain beers
        val beers = beersApi?.map(beerMapper::toDomain).orEmpty()

        // Add beers to current list
        return cachedData.apply {
            addAll(beers)
        }
    }
}

private const val FIRST_PAGE = 1
