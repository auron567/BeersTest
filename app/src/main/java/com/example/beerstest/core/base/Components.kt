package com.example.beerstest.core.base

interface ViewState

interface ViewEvent

interface ViewEffect

sealed class Loader {
    object Idle : Loader()
    object Loading : Loader()
}
