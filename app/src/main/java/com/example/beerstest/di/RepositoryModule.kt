package com.example.beerstest.di

import com.example.beerstest.data.repository.BeerRepositoryImpl
import com.example.beerstest.domain.repository.BeerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideBeerRepository(repository: BeerRepositoryImpl): BeerRepository
}
