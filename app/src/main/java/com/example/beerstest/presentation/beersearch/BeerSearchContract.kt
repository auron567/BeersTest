package com.example.beerstest.presentation.beersearch

import com.example.beerstest.core.base.ViewEffect
import com.example.beerstest.core.base.ViewEvent
import com.example.beerstest.core.base.ViewState

class BeerSearchContract {

    sealed class Event : ViewEvent {
        class OnNameChanged(val name: String) : Event()
        object OnSearchClicked : Event()
    }

    sealed class Effect : ViewEffect {
        class BackToBeerList(val beerName: String) : Effect()
    }

    data class State(
        val beerName: String,
        val isButtonEnabled: Boolean
    ) : ViewState

    companion object {
        fun initState() = State(
            beerName = "",
            isButtonEnabled = false
        )
    }
}
