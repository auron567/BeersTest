package com.example.beerstest.presentation.beersearch

import com.example.beerstest.core.base.ViewEffect
import com.example.beerstest.core.base.ViewEvent
import com.example.beerstest.core.base.ViewState

class BeerSearchContract {

    sealed class Event : ViewEvent

    sealed class Effect : ViewEffect

    class State : ViewState

    companion object {
        fun initState() = State()
    }
}
