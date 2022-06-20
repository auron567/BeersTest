package com.example.beerstest.presentation.beerdetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.beerstest.domain.model.BeerEntity
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

    private lateinit var viewModel: BeerDetailViewModel

    @Test
    fun `view model set beer on init`() = runTest {
        // Init view model
        val beer = createBeerEntity()
        setupViewModel(beer)

        // Assertion
        viewModel.currentState.beer shouldBe beer
    }

    @Test
    fun `view model launch proper effect when back is clicked`() = runTest {
        // Init view model
        setupViewModel(createBeerEntity())

        // Call view model
        viewModel.launchEvent(BeerDetailContract.Event.OnBackClicked)

        // Assertion
        viewModel.effect.test {
            awaitItem().shouldBeTypeOf<BeerDetailContract.Effect.Pop>()
        }
    }

    private fun setupViewModel(beer: BeerEntity) {
        val savedStateHandle = SavedStateHandle().apply {
            set("beer", beer)
        }
        viewModel = BeerDetailViewModel(savedStateHandle)
    }
}
