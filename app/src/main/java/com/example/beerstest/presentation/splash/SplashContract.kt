package com.example.beerstest.presentation.splash

import com.example.beerstest.core.base.ViewEffect
import com.example.beerstest.core.base.ViewEvent
import com.example.beerstest.core.base.ViewState

class SplashContract {

    sealed class Event : ViewEvent

    sealed class Effect : ViewEffect {
        object GoToBeerList : Effect()
    }

    class State : ViewState

    companion object {
        fun initState() = State()
    }
}
