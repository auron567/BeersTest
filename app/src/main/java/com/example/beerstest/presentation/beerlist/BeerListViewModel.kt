package com.example.beerstest.presentation.beerlist

import androidx.lifecycle.viewModelScope
import com.example.beerstest.core.base.BaseViewModel
import com.example.beerstest.core.dispatchers.DispatcherProvider
import com.example.beerstest.domain.model.BeerEntity
import com.example.beerstest.domain.usecase.GetBeersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class BeerListViewModel @Inject constructor(
    private val getBeersUseCase: GetBeersUseCase,
    private val dispatchers: DispatcherProvider
) : BaseViewModel<BeerListContract.Event, BeerListContract.State, BeerListContract.Effect>() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        hideLoader()
        launchEffect { BeerListContract.Effect.ShowErrorSnackbar }
    }

    override fun createInitialState() = BeerListContract.initState()

    init {
        getBeers()
    }

    private fun getBeers() {
        viewModelScope.launch(exceptionHandler) {
            showLoader()
            val beers = withContext(dispatchers.io) {
                getBeersUseCase()
            }
            hideLoader()

            setState {
                copy(
                    beers = beers.toMutableList(),
                    showEmptyView = beers.isEmpty()
                )
            }
        }
    }

    override fun handleEvent(event: BeerListContract.Event) {
        when (event) {
            is BeerListContract.Event.OnBeerClicked -> onBeerClicked(event.beer)
            is BeerListContract.Event.OnLoadMoreBeers -> getBeers()
        }
    }

    private fun onBeerClicked(beer: BeerEntity) {
        launchEffect { BeerListContract.Effect.GoToBeerDetail(beer) }
    }
}
