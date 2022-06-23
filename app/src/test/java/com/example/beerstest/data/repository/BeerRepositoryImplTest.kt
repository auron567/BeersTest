package com.example.beerstest.data.repository

import com.example.beerstest.core.exceptions.NetworkError
import com.example.beerstest.core.extensions.fromJsonToResponseBody
import com.example.beerstest.data.database.BeerDao
import com.example.beerstest.data.mapper.BeerApiToDomainMapper
import com.example.beerstest.data.mapper.BeerDomainToDbMapper
import com.example.beerstest.data.network.BeerService
import com.example.beerstest.data.network.model.BeerResponse
import com.example.beerstest.utils.JSON_SERVICE_UNAVAILABLE
import com.example.beerstest.utils.createBeerDb
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

    @MockK(relaxed = true) lateinit var beerService: BeerService

    @MockK(relaxed = true) lateinit var beerDao: BeerDao

    @MockK(relaxed = true) lateinit var apiToDomainMapper: BeerApiToDomainMapper

    @MockK(relaxed = true) lateinit var domainToDbMapper: BeerDomainToDbMapper

    private lateinit var repository: BeerRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = BeerRepositoryImpl(beerService, beerDao, apiToDomainMapper, domainToDbMapper)
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
        every { apiToDomainMapper.map(beerApiFirst) } returns beerDomainFirst
        every { apiToDomainMapper.map(beerApiSecond) } returns beerDomainSecond

        // Call repository
        val beers = repository.getBeers(
            isStart = true,
            filterName = null
        )

        // Assertion
        beers.shouldContainExactly(beersDomain)
    }

    @Test
    fun `repository return empty list when remote body is empty`() = runTest {
        // Stub service
        val response = Response.success<List<BeerResponse>>(emptyList())
        coEvery { beerService.getBeers(any(), any()) } returns response

        // Call repository
        val beers = repository.getBeers(
            isStart = true,
            filterName = null
        )

        // Assertion
        beers.shouldBeEmpty()
    }

    @Test
    fun `repository return empty list when remote body is null`() = runTest {
        // Stub service
        val response = Response.success<List<BeerResponse>>(null)
        coEvery { beerService.getBeers(any(), any()) } returns response

        // Call repository
        val beers = repository.getBeers(
            isStart = true,
            filterName = null
        )

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
            repository.getBeers(
                isStart = true,
                filterName = null
            )
        }
    }

    @Test
    fun `repository call service with proper beer name argument`() = runTest {
        // Stub service
        val response = Response.success<List<BeerResponse>>(emptyList())
        coEvery { beerService.getBeers(any(), any()) } returns response

        // Call repository
        val beerName = "Buzz"
        repository.getBeers(
            isStart = true,
            filterName = beerName
        )

        // Verify
        coVerify {
            beerService.getBeers(
                beerName = beerName,
                page = any()
            )
        }
    }

    @Test
    fun `repository call service with first page on start`() = runTest {
        // Stub service
        val response = Response.success<List<BeerResponse>>(emptyList())
        coEvery { beerService.getBeers(any(), any()) } returns response

        // Call repository
        repository.getBeers(
            isStart = true,
            filterName = null
        )

        // Verify
        coVerify {
            beerService.getBeers(
                beerName = any(),
                page = 1
            )
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
            repository.getBeers(
                isStart = false,
                filterName = null
            )
        }

        // Verify
        coVerifyOrder {
            range.forEach { index ->
                beerService.getBeers(
                    beerName = any(),
                    page = index
                )
            }
        }
    }

    @Test
    fun `repository call dao with proper beer when save favorite`() = runTest {
        // Models
        val beerDomain = createBeerEntity(name = "Trashy Blonde")
        val beerDb = createBeerDb(name = "Trashy Blonde")

        // Stub mapper
        every { domainToDbMapper.map(beerDomain) } returns beerDb

        // Call repository
        repository.saveFavorite(beerDomain)

        // Verify
        coVerify {
            beerDao.insertBeer(beerDb)
        }
    }

    @Test
    fun `repository call dao with proper id when remove favorite`() = runTest {
        // Call repository
        val id = 6
        repository.removeFavorite(id)

        // Verify
        coVerify {
            beerDao.deleteBeer(id)
        }
    }

    @Test
    fun `repository call dao with proper id when check favorite`() = runTest {
        // Call repository
        val id = 12
        repository.isFavorite(id)

        // Verify
        coVerify {
            beerDao.isBeerExists(id)
        }
    }
}
