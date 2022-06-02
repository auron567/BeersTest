package com.example.beerstest.presentation.beerdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.beerstest.core.base.BaseViewModel
import com.example.beerstest.domain.model.BeerEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class BeerDetailViewModel @AssistedInject constructor(
    @Assisted private val beer: BeerEntity
) : BaseViewModel<BeerDetailContract.Event, BeerDetailContract.State, BeerDetailContract.Effect>() {

    @AssistedFactory
    interface Factory {
        fun create(beer: BeerEntity): BeerDetailViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            beer: BeerEntity
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(beer) as T
            }
        }
    }

    override fun createInitialState() = BeerDetailContract.initState()

    init {
        setBeer(beer)
    }

    private fun setBeer(beer: BeerEntity) {
        setState {
            copy(beer = beer)
        }
    }

    override fun handleEvent(event: BeerDetailContract.Event) {
        when (event) {
            is BeerDetailContract.Event.OnBackClicked -> onBackClicked()
        }
    }

    private fun onBackClicked() {
        launchEffect { BeerDetailContract.Effect.Pop }
    }
}
