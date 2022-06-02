package com.example.beerstest.presentation.beerdetail

import com.example.beerstest.core.base.ViewEffect
import com.example.beerstest.core.base.ViewEvent
import com.example.beerstest.core.base.ViewState
import com.example.beerstest.domain.model.BeerEntity

class BeerDetailContract {

    sealed class Event : ViewEvent {
        object OnBackClicked : Event()
    }

    sealed class Effect : ViewEffect {
        object Pop : Effect()
    }

    data class State(
        val beer: BeerEntity
    ) : ViewState

    companion object {
        fun initState() = State(
            beer = BeerEntity()
        )
    }
}
