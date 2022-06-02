package com.example.beerstest.presentation.beerlist

import com.example.beerstest.core.base.ViewEffect
import com.example.beerstest.core.base.ViewEvent
import com.example.beerstest.core.base.ViewState
import com.example.beerstest.domain.model.BeerEntity

class BeerListContract {

    sealed class Event : ViewEvent {
        class OnBeerClicked(val beer: BeerEntity) : Event()
        object OnSearchClicked : Event()
        object OnLoadMoreBeers : Event()
    }

    sealed class Effect : ViewEffect {
        class GoToBeerDetail(val beer: BeerEntity) : Effect()
        object GoToBeerSearch : Effect()
        object ShowErrorSnackbar : Effect()
    }

    data class State(
        val beers: List<BeerEntity>,
        val showEmptyView: Boolean
    ) : ViewState

    companion object {
        fun initState() = State(
            beers = emptyList(),
            showEmptyView = false
        )
    }
}
