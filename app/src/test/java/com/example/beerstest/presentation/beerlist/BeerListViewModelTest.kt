package com.example.beerstest.presentation.beerlist

import app.cash.turbine.test
import com.example.beerstest.core.exceptions.NetworkError
import com.example.beerstest.domain.usecase.GetBeersUseCase
import com.example.beerstest.utils.CoroutineTestRule
import com.example.beerstest.utils.createBeerEntity
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BeerListViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @MockK(relaxed = true)
    lateinit var getBeersUseCase: GetBeersUseCase

    private lateinit var viewModel: BeerListViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `view model set state properly when get beers`() = runTest {
        // Stub use case
        val mockBeers = listOf(createBeerEntity(), createBeerEntity())
        coEvery { getBeersUseCase.invoke(any(), any()) } returns mockBeers

        // Init view model
        setupViewModel()

        // Assertion
        assertSoftly(viewModel.currentState) {
            beers.shouldContainExactly(mockBeers)
            showEmptyView.shouldBeFalse()
        }
    }

    @Test
    fun `view model set state properly when beer list is empty`() = runTest {
        // Stub use case
        coEvery { getBeersUseCase.invoke(any(), any()) } returns emptyList()

        // Init view model
        setupViewModel()

        // Assertion
        assertSoftly(viewModel.currentState) {
            beers.shouldBeEmpty()
            showEmptyView.shouldBeTrue()
        }
    }

    @Test
    fun `view model call use case with proper arguments on init`() = runTest {
        // Init view model
        setupViewModel()

        // Verify
        coVerify {
            getBeersUseCase.invoke(
                isStart = true,
                filterName = null
            )
        }
    }

    @Test
    fun `view model call use case with proper arguments when beer is searched`() = runTest {
        // Init view model
        setupViewModel()

        // Call view model
        val beerName = "Buzz"
        viewModel.launchEvent(BeerListContract.Event.OnBeerSearched(beerName))

        // Verify
        coVerify {
            getBeersUseCase.invoke(
                isStart = true,
                filterName = beerName
            )
        }
    }

    @Test
    fun `view model call use case with proper arguments when more beers are loaded`() = runTest {
        // Init view model
        setupViewModel()

        // Call view model
        viewModel.launchEvent(BeerListContract.Event.OnLoadMoreBeers)

        // Verify
        coVerify {
            getBeersUseCase.invoke(
                isStart = false,
                filterName = null
            )
        }
    }

    @Test
    fun `view model launch proper effect when exception is thrown`() = runTest {
        // Stub use case
        coEvery { getBeersUseCase.invoke(any(), any()) } throws NetworkError.ServiceUnavailable()

        // Init view model
        setupViewModel()

        // Assertion
        viewModel.effect.test {
            awaitItem().shouldBeTypeOf<BeerListContract.Effect.ShowErrorSnackbar>()
        }
    }

    @Test
    fun `view model launch proper effect when search is clicked`() = runTest {
        // Init view model
        setupViewModel()

        // Call view model
        viewModel.launchEvent(BeerListContract.Event.OnSearchClicked)

        // Assertion
        viewModel.effect.test {
            awaitItem().shouldBeTypeOf<BeerListContract.Effect.GoToBeerSearch>()
        }
    }

    @Test
    fun `view model launch proper effect when beer is clicked`() = runTest {
        // Init view model
        setupViewModel()

        // Call view model
        val beer = createBeerEntity()
        viewModel.launchEvent(BeerListContract.Event.OnBeerClicked(beer))

        // Assertion
        viewModel.effect.test {
            awaitItem().shouldBeTypeOf<BeerListContract.Effect.GoToBeerDetail>()
        }
    }

    private fun setupViewModel() {
        viewModel = BeerListViewModel(getBeersUseCase, coroutineTestRule.testDispatcherProvider)
    }
}
