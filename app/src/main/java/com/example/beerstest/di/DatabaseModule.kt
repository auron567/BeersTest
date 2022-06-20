package com.example.beerstest.di

import android.content.Context
import androidx.room.Room
import com.example.beerstest.BuildConfig
import com.example.beerstest.data.database.BeerDao
import com.example.beerstest.data.database.BeerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideBeerDatabase(@ApplicationContext context: Context): BeerDatabase {
        return Room.databaseBuilder(context, BeerDatabase::class.java, BuildConfig.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideBeerDao(database: BeerDatabase): BeerDao {
        return database.beerDao()
    }
}
