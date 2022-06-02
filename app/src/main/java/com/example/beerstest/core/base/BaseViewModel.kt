package com.example.beerstest.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : ViewEvent, State : ViewState, Effect : ViewEffect> : ViewModel() {

    private val initialState: State by lazy { createInitialState() }
    protected abstract fun createInitialState(): State

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect: Channel<Effect> = Channel(capacity = BUFFERED)
    val effect: Flow<Effect> = _effect.receiveAsFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    private val event: SharedFlow<Event> = _event.asSharedFlow()

    private val _loader: MutableStateFlow<Loader> = MutableStateFlow(Loader.Idle)
    val loader: StateFlow<Loader> = _loader.asStateFlow()

    val currentState: State
        get() = state.value

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch { event.collect(::handleEvent) }
    }

    abstract fun handleEvent(event: Event)

    fun launchEvent(event: Event) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }

    protected fun launchEffect(builder: () -> Effect) {
        val newEffect = builder()
        viewModelScope.launch { _effect.send(newEffect) }
    }

    protected fun setState(reducer: State.() -> State) {
        val newState = currentState.reducer()
        _state.value = newState
    }

    protected fun showLoader() {
        _loader.value = Loader.Loading
    }

    protected fun hideLoader() {
        _loader.value = Loader.Idle
    }
}
