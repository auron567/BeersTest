package com.example.beerstest.presentation.beersearch

import app.cash.turbine.test
import com.example.beerstest.utils.ViewModelTest
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import io.kotest.matchers.types.shouldBeTypeOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class BeerSearchViewModelTest : ViewModelTest() {

    private lateinit var viewModel: BeerSearchViewModel

    @Before
    fun setup() {
        viewModel = BeerSearchViewModel()
    }

    @Test
    fun `view model set state properly on init`() = runTest {
        // Assertion
        assertSoftly(viewModel.currentState) {
            beerName.shouldBeEmpty()
            isButtonEnabled.shouldBeFalse()
        }
    }

    @Test
    fun `view model set state properly when name is typed`() = runTest {
        // Call view model
        val name = "Buzz"
        viewModel.launchEvent(BeerSearchContract.Event.OnNameChanged(name))

        // Assertion
        assertSoftly(viewModel.currentState) {
            beerName.shouldBe(name)
            isButtonEnabled.shouldBeTrue()
        }
    }

    @Test
    fun `view model set state properly when typed name is empty`() = runTest {
        // Call view model
        val name = ""
        viewModel.launchEvent(BeerSearchContract.Event.OnNameChanged(name))

        // Assertion
        assertSoftly(viewModel.currentState) {
            beerName.shouldBe(name)
            isButtonEnabled.shouldBeFalse()
        }
    }

    @Test
    fun `view model set state properly when typed name is blank`() = runTest {
        // Call view model
        val name = " "
        viewModel.launchEvent(BeerSearchContract.Event.OnNameChanged(name))

        // Assertion
        assertSoftly(viewModel.currentState) {
            beerName.shouldBe(name)
            isButtonEnabled.shouldBeFalse()
        }
    }

    @Test
    fun `view model launch proper effect when search is clicked`() = runTest {
        // Call view model
        viewModel.launchEvent(BeerSearchContract.Event.OnSearchClicked)

        // Assertion
        viewModel.effect.test {
            awaitItem().shouldBeTypeOf<BeerSearchContract.Effect.BackToBeerList>()
        }
    }
}
