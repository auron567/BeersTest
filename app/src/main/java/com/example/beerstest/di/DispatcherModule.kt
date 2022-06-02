package com.example.beerstest.di

import com.example.beerstest.core.dispatchers.DefaultDispatcherProvider
import com.example.beerstest.core.dispatchers.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {

    @Binds
    @Singleton
    abstract fun provideDispatcherProvider(dispatcher: DefaultDispatcherProvider): DispatcherProvider
}
