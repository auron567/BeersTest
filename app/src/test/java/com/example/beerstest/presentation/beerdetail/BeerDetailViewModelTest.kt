package com.example.beerstest.presentation.beerdetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.beerstest.domain.model.BeerEntity
import com.example.beerstest.domain.usecase.IsFavoriteUseCase
import com.example.beerstest.domain.usecase.RemoveFavoriteUseCase
import com.example.beerstest.domain.usecase.SaveFavoriteUseCase
import com.example.beerstest.utils.ViewModelTest
import com.example.beerstest.utils.createBeerEntity
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class BeerDetailViewModelTest : ViewModelTest() {

    @MockK(relaxed = true) lateinit var isFavoriteUseCase: IsFavoriteUseCase

    @MockK lateinit var saveFavoriteUseCase: SaveFavoriteUseCase

    @MockK lateinit var removeFavoriteUseCase: RemoveFavoriteUseCase

    private lateinit var viewModel: BeerDetailViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `view model set beer on init`() = runTest {
        // Init view model
        val beer = createBeerEntity()
        setupViewModel(beer)

        // Assertion
        viewModel.currentState.currentBeer shouldBe beer
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
        viewModel = BeerDetailViewModel(
            isFavoriteUseCase,
            saveFavoriteUseCase,
            removeFavoriteUseCase,
            savedStateHandle
        )
    }
}
