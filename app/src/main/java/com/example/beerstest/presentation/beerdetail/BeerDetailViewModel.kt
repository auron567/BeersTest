package com.example.beerstest.presentation.beerdetail

import androidx.lifecycle.SavedStateHandle
import com.example.beerstest.core.base.BaseViewModel
import com.example.beerstest.domain.model.BeerEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BeerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<BeerDetailContract.Event, BeerDetailContract.State, BeerDetailContract.Effect>() {

    private val args = BeerDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)

    override fun createInitialState() = BeerDetailContract.initState()

    init {
        setBeer(args.beer)
    }

    private fun setBeer(beer: BeerEntity) {
        setState {
            copy(beer = beer)
        }
    }

    override fun handleEvent(event: BeerDetailContract.Event) {
        when (event) {
            is BeerDetailContract.Event.OnFavoriteClicked -> onFavoriteClicked()
            is BeerDetailContract.Event.OnBackClicked -> onBackClicked()
        }
    }

    private fun onFavoriteClicked() {
        // TODO: check favorite
    }

    private fun onBackClicked() {
        launchEffect { BeerDetailContract.Effect.Pop }
    }
}
