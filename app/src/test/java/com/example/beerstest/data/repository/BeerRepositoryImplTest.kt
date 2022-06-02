package com.example.beerstest.data.repository

import com.example.beerstest.core.exceptions.NetworkError
import com.example.beerstest.core.extensions.fromJsonToResponseBody
import com.example.beerstest.data.network.mapper.BeerApiToDomainMapper
import com.example.beerstest.data.network.model.BeerResponse
import com.example.beerstest.data.network.service.BeerService
import com.example.beerstest.utils.JSON_SERVICE_UNAVAILABLE
import com.example.beerstest.utils.createBeerEntity
import com.example.beerstest.utils.createBeerResponse
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class BeerRepositoryImplTest {

    @MockK lateinit var beerService: BeerService

    @MockK lateinit var beerMapper: BeerApiToDomainMapper

    private lateinit var repository: BeerRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = BeerRepositoryImpl(beerService, beerMapper)
    }

    @Test
    fun `repository return beers when remote works as expected`() = runTest {
        // Beers api model
        val beerApiFirst = createBeerResponse(name = "Buzz")
        val beerApiSecond = createBeerResponse(name = "Trashy Blonde")
        val beersApi = listOf(beerApiFirst, beerApiSecond)

        // Beers domain model
        val beerDomainFirst = createBeerEntity(name = "Buzz")
        val beerDomainSecond = createBeerEntity(name = "Trashy Blonde")
        val beersDomain = listOf(beerDomainFirst, beerDomainSecond)

        // Stub service
        val response = Response.success(beersApi)
        coEvery { beerService.getBeers(any(), any()) } returns response

        // Stub mapper
        every { beerMapper.toDomain(beerApiFirst) } returns beerDomainFirst
        every { beerMapper.toDomain(beerApiSecond) } returns beerDomainSecond

        // Call repository
        val beers = repository.getBeers()

        // Assertion
        beers.shouldContainExactly(beersDomain)
    }

    @Test
    fun `repository return empty list when remote body is empty`() = runTest {
        // Stub service
        val response = Response.success<List<BeerResponse>>(emptyList())
        coEvery { beerService.getBeers(any(), any()) } returns response

        // Call repository
        val beers = repository.getBeers()

        // Assertion
        beers.shouldBeEmpty()
    }

    @Test
    fun `repository return empty list when remote body is null`() = runTest {
        // Stub service
        val response = Response.success<List<BeerResponse>>(null)
        coEvery { beerService.getBeers(any(), any()) } returns response

        // Call repository
        val beers = repository.getBeers()

        // Assertion
        beers.shouldBeEmpty()
    }

    @Test
    fun `repository throw exception with remote error`() = runTest {
        shouldThrow<NetworkError> {
            // Stub service
            val body = JSON_SERVICE_UNAVAILABLE.fromJsonToResponseBody()
            val response = Response.error<List<BeerResponse>>(500, body)
            coEvery { beerService.getBeers(any(), any()) } returns response

            // Call repository
            repository.getBeers()
        }
    }

    @Test
    fun `repository call service with first page on start`() = runTest {
        // Stub service
        val response = Response.success<List<BeerResponse>>(emptyList())
        coEvery { beerService.getBeers(any(), any()) } returns response

        // Call repository
        repository.getBeers()

        // Verify
        coVerify {
            beerService.getBeers(page = 1)
        }
    }

    @Test
    fun `repository call service with proper next page argument`() = runTest {
        // Stub service
        val response = Response.success<List<BeerResponse>>(emptyList())
        coEvery { beerService.getBeers(any(), any()) } returns response

        // Call repository
        val range = (1..4)
        repeat(range.count()) {
            repository.getBeers()
        }

        // Verify
        coVerifyOrder {
            range.forEach { index ->
                beerService.getBeers(page = index)
            }
        }
    }
}
