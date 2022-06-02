package com.example.beerstest.core.dispatchers

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultDispatcherProvider @Inject constructor() : DispatcherProvider {

    override val main: CoroutineDispatcher get() = Dispatchers.Main

    override val default: CoroutineDispatcher get() = Dispatchers.Default

    override val io: CoroutineDispatcher get() = Dispatchers.IO

    override val unconfined: CoroutineDispatcher get() = Dispatchers.Unconfined
}
