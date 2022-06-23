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
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class BeerDetailViewModelTest : ViewModelTest() {

    @MockK(relaxed = true) lateinit var isFavoriteUseCase: IsFavoriteUseCase

    @MockK(relaxed = true) lateinit var saveFavoriteUseCase: SaveFavoriteUseCase

    @MockK(relaxed = true) lateinit var removeFavoriteUseCase: RemoveFavoriteUseCase

    private lateinit var viewModel: BeerDetailViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `view model set proper beer on init`() = runTest {
        // Init view model
        val beer = createBeerEntity()
        setupViewModel(beer)

        // Assertion
        viewModel.currentState.currentBeer shouldBe beer
    }

    @Test
    fun `view model set proper favorite on init`() = runTest {
        // Stub use case
        val isFavorite = true
        val flow = flow { emit(isFavorite) }
        every { isFavoriteUseCase.invoke(any()) } returns flow

        // Init view model
        setupViewModel(createBeerEntity())

        // Assertion
        viewModel.currentState.isFavorite shouldBe isFavorite
    }

    @Test
    fun `view model call use case with proper id when check favorite`() = runTest {
        // Init view model
        val beer = createBeerEntity(id = 8)
        setupViewModel(beer)

        // Verify
        verify {
            isFavoriteUseCase.invoke(beer.id)
        }
    }

    @Test
    fun `view model call use case with proper id when remove favorite`() = runTest {
        // Stub use case
        val flow = flow { emit(true) }
        every { isFavoriteUseCase.invoke(any()) } returns flow

        // Init view model
        val beer = createBeerEntity(id = 10)
        setupViewModel(beer)

        // Call view model
        viewModel.launchEvent(BeerDetailContract.Event.OnFavoriteClicked)

        // Verify
        coVerify {
            removeFavoriteUseCase.invoke(beer.id)
        }
    }

    @Test
    fun `view model call use case with proper beer when save favorite`() = runTest {
        // Stub use case
        val flow = flow { emit(false) }
        every { isFavoriteUseCase.invoke(any()) } returns flow

        // Init view model
        val beer = createBeerEntity()
        setupViewModel(beer)

        // Call view model
        viewModel.launchEvent(BeerDetailContract.Event.OnFavoriteClicked)

        // Verify
        coVerify {
            saveFavoriteUseCase.invoke(beer)
        }
    }

    @Test
    fun `view model launch proper effect when favorite is removed`() = runTest {
        // Stub use case
        val flow = flow { emit(true) }
        every { isFavoriteUseCase.invoke(any()) } returns flow

        // Init view model
        setupViewModel(createBeerEntity())

        // Call view model
        viewModel.launchEvent(BeerDetailContract.Event.OnFavoriteClicked)

        // Assertion
        viewModel.effect.test {
            awaitItem().shouldBeTypeOf<BeerDetailContract.Effect.FavoriteRemoved>()
        }
    }

    @Test
    fun `view model launch proper effect when favorite is saved`() = runTest {
        // Stub use case
        val flow = flow { emit(false) }
        every { isFavoriteUseCase.invoke(any()) } returns flow

        // Init view model
        setupViewModel(createBeerEntity())

        // Call view model
        viewModel.launchEvent(BeerDetailContract.Event.OnFavoriteClicked)

        // Assertion
        viewModel.effect.test {
            awaitItem().shouldBeTypeOf<BeerDetailContract.Effect.FavoriteSaved>()
        }
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
