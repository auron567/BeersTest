package com.example.beerstest.domain.mapper

import com.example.beerstest.utils.createBeerEntity
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test

class BeerDomainToDbMapperTest {

    private lateinit var mapper: BeerDomainToDbMapper

    @Before
    fun setup() {
        mapper = BeerDomainToDbMapper()
    }

    @Test
    fun `mapper properly map beer domain model to db model`() {
        // Input model
        val beerEntity = createBeerEntity(
            id = 4,
            name = "Pilsen Lager",
            slogan = "Japanese Citrus Berliner Weisse.",
            description = "Japanese citrus fruit intensifies the sour nature of this German classic.",
            imageUrl = "https://images.punkapi.com/v2/4.png",
            productionYear = "2015"
        )

        // Call mapper
        val beerDb = mapper.map(beerEntity)

        // Assertion
        assertSoftly(beerDb) {
            id shouldBe 4
            name shouldBe "Pilsen Lager"
        }
    }
}
