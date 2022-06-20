package com.example.beerstest.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.beerstest.data.database.model.BeerDb
import kotlinx.coroutines.flow.Flow

@Dao
interface BeerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBeer(beer: BeerDb)

    @Query("DELETE FROM beer WHERE id = :id")
    suspend fun deleteBeer(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM beer WHERE id = :id LIMIT 1)")
    fun isBeerExists(id: Int): Flow<Boolean>
}
