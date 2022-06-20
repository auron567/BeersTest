package com.example.beerstest.data.network.mapper

import com.example.beerstest.utils.createBeerResponse
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test

class BeerApiToDomainMapperTest {

    private lateinit var mapper: BeerApiToDomainMapper

    @Before
    fun setup() {
        mapper = BeerApiToDomainMapper()
    }

    @Test
    fun `mapper properly map beer api model to domain model`() {
        // Input model
        val beerResponse = createBeerResponse(
            id = 4,
            name = "Pilsen Lager",
            tagline = "Japanese Citrus Berliner Weisse.",
            description = "Japanese citrus fruit intensifies the sour nature of this German classic.",
            imageUrl = "https://images.punkapi.com/v2/4.png",
            firstBrewed = "11/2015"
        )

        // Call mapper
        val beerEntity = mapper.map(beerResponse)

        // Assertion
        assertSoftly(beerEntity) {
            id shouldBe 4
            name shouldBe "Pilsen Lager"
            slogan shouldBe "Japanese Citrus Berliner Weisse."
            description shouldBe "Japanese citrus fruit intensifies the sour nature of this German classic."
            imageUrl shouldBe "https://images.punkapi.com/v2/4.png"
            productionYear shouldBe "2015"
        }
    }
}
