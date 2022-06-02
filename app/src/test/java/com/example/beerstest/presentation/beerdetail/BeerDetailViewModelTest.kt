package com.example.beerstest.presentation.beerdetail

import app.cash.turbine.test
import com.example.beerstest.utils.CoroutineTestRule
import com.example.beerstest.utils.createBeerEntity
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class BeerDetailViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `view model set beer on init`() = runTest {
        // Init view model
        val beer = createBeerEntity()
        val viewModel = BeerDetailViewModel(beer)

        // Assertion
        viewModel.currentState.beer shouldBe beer
    }

    @Test
    fun `view model launch proper effect when back is clicked`() = runTest {
        // Init view model
        val beer = createBeerEntity()
        val viewModel = BeerDetailViewModel(beer)

        // Call view model
        viewModel.launchEvent(BeerDetailContract.Event.OnBackClicked)

        // Assertion
        viewModel.effect.test {
            awaitItem().shouldBeTypeOf<BeerDetailContract.Effect.Pop>()
        }
    }
}
