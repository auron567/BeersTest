package com.example.beerstest.presentation.beerdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.beerstest.core.base.BaseViewModel
import com.example.beerstest.domain.model.BeerEntity
import com.example.beerstest.domain.usecase.IsFavoriteUseCase
import com.example.beerstest.domain.usecase.RemoveFavoriteUseCase
import com.example.beerstest.domain.usecase.SaveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class BeerDetailViewModel @Inject constructor(
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<BeerDetailContract.Event, BeerDetailContract.State, BeerDetailContract.Effect>() {

    private val args = BeerDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val beer: BeerEntity = args.beer

    override fun createInitialState() = BeerDetailContract.initState()

    init {
        setBeer()
        observeFavorite()
    }

    private fun setBeer() {
        setState {
            copy(currentBeer = beer)
        }
    }

    private fun observeFavorite() {
        viewModelScope.launch {
            isFavoriteUseCase(beer.id).collect { isFavorite ->
                setState {
                    copy(isFavorite = isFavorite)
                }
            }
        }
    }

    override fun handleEvent(event: BeerDetailContract.Event) {
        when (event) {
            is BeerDetailContract.Event.OnFavoriteClicked -> onFavoriteClicked()
            is BeerDetailContract.Event.OnBackClicked -> onBackClicked()
        }
    }

    private fun onFavoriteClicked() {
        viewModelScope.launch {
            if (currentState.isFavorite) {
                removeFavoriteUseCase(beer.id)
                launchEffect { BeerDetailContract.Effect.FavoriteRemoved }
            } else {
                saveFavoriteUseCase(beer)
                launchEffect { BeerDetailContract.Effect.FavoriteSaved }
            }
        }
    }

    private fun onBackClicked() {
        launchEffect { BeerDetailContract.Effect.Pop }
    }
}
