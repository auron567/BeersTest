package com.example.beerstest.presentation.beersearch

import com.example.beerstest.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BeerSearchViewModel @Inject constructor() :
    BaseViewModel<BeerSearchContract.Event, BeerSearchContract.State, BeerSearchContract.Effect>() {

    override fun createInitialState() = BeerSearchContract.initState()

    override fun handleEvent(event: BeerSearchContract.Event) {
    }
}
