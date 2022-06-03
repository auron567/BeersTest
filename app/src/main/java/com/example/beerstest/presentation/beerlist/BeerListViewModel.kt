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
import timber.log.Timber

@HiltViewModel
class BeerListViewModel @Inject constructor(
    private val getBeersUseCase: GetBeersUseCase,
    private val dispatchers: DispatcherProvider
) : BaseViewModel<BeerListContract.Event, BeerListContract.State, BeerListContract.Effect>() {

    // Filter to search for all beers matching name
    // If null, no filter is applied
    private var filterName: String? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable: Throwable ->
        Timber.e(throwable)
        hideLoader()
        launchEffect { BeerListContract.Effect.ShowErrorSnackbar }
    }

    override fun createInitialState() = BeerListContract.initState()

    init {
        getBeers(isStart = true)
    }

    private fun getBeers(isStart: Boolean) {
        viewModelScope.launch(exceptionHandler) {
            showLoader()
            val beers = withContext(dispatchers.io) {
                getBeersUseCase(isStart, filterName)
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
            is BeerListContract.Event.OnBeerSearched -> onBeerSearched(event.name)
            is BeerListContract.Event.OnLoadMoreBeers -> getBeers(isStart = false)
            is BeerListContract.Event.OnSearchClicked -> onSearchClicked()
        }
    }

    private fun onBeerSearched(name: String) {
        // Update filter name
        filterName = name

        // Load new beer list
        getBeers(isStart = true)
    }

    private fun onBeerClicked(beer: BeerEntity) {
        launchEffect { BeerListContract.Effect.GoToBeerDetail(beer) }
    }

    private fun onSearchClicked() {
        launchEffect { BeerListContract.Effect.GoToBeerSearch }
    }
}
