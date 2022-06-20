package com.example.beerstest.presentation.beerdetail

import com.example.beerstest.core.base.ViewEffect
import com.example.beerstest.core.base.ViewEvent
import com.example.beerstest.core.base.ViewState
import com.example.beerstest.domain.model.BeerEntity

class BeerDetailContract {

    sealed class Event : ViewEvent {
        object OnFavoriteClicked : Event()
        object OnBackClicked : Event()
    }

    sealed class Effect : ViewEffect {
        object FavoriteSaved : Effect()
        object FavoriteRemoved : Effect()
        object Pop : Effect()
    }

    data class State(
        val currentBeer: BeerEntity,
        val isFavorite: Boolean
    ) : ViewState

    companion object {
        fun initState() = State(
            currentBeer = BeerEntity(),
            isFavorite = false
        )
    }
}
