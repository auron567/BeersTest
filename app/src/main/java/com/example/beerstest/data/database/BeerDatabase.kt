package com.example.beerstest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.beerstest.BuildConfig
import com.example.beerstest.data.database.model.BeerDb

@Database(entities = [BeerDb::class], version = BuildConfig.DATABASE_VERSION, exportSchema = false)
abstract class BeerDatabase : RoomDatabase() {

    abstract fun beerDao(): BeerDao
}
