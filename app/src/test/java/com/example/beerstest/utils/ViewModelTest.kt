package com.example.beerstest.utils

import org.junit.Rule

abstract class ViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()
}
