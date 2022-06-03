package com.example.beerstest.presentation.beersearch

import com.example.beerstest.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BeerSearchViewModel @Inject constructor() :
    BaseViewModel<BeerSearchContract.Event, BeerSearchContract.State, BeerSearchContract.Effect>() {

    override fun createInitialState() = BeerSearchContract.initState()

    override fun handleEvent(event: BeerSearchContract.Event) {
        when (event) {
            is BeerSearchContract.Event.OnNameChanged -> onNameChanged(event.name)
            is BeerSearchContract.Event.OnSearchClicked -> onSearchClicked()
        }
    }

    private fun onNameChanged(name: String) {
        setState {
            copy(
                beerName = name,
                isButtonEnabled = name.isNotBlank()
            )
        }
    }

    private fun onSearchClicked() {
        launchEffect { BeerSearchContract.Effect.BackToBeerList(currentState.beerName) }
    }
}
