package com.example.beerstest.data.network

import com.example.beerstest.data.network.model.BeerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerService {

    @GET("beers")
    suspend fun getBeers(
        @Query("beer_name") beerName: String?,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int = PAGE_SIZE,
    ): Response<List<BeerResponse>>
}

private const val PAGE_SIZE = 25
