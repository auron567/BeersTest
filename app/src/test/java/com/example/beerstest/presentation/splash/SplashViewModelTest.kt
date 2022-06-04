package com.example.beerstest.presentation.splash

import app.cash.turbine.test
import com.example.beerstest.utils.CoroutineTestRule
import io.kotest.matchers.types.shouldBeTypeOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SplashViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: SplashViewModel

    @Before
    fun setup() {
        viewModel = SplashViewModel()
    }

    @Test
    fun `view model launch proper effect on init`() = runTest {
        // Assertion
        viewModel.effect.test {
            awaitItem().shouldBeTypeOf<SplashContract.Effect.GoToBeerList>()
        }
    }
}
