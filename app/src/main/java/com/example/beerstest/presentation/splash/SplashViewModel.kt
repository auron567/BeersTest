package com.example.beerstest.presentation.splash

import androidx.lifecycle.viewModelScope
import com.example.beerstest.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor() :
    BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {

    override fun createInitialState() = SplashContract.initState()

    init {
        launchHome()
    }

    private fun launchHome() {
        viewModelScope.launch {
            delay(ANIMATION_DELAY)
            launchEffect { SplashContract.Effect.GoToBeerList }
        }
    }

    override fun handleEvent(event: SplashContract.Event) {
    }
}

private const val ANIMATION_DELAY = 1500L
