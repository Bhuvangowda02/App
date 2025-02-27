package com.example.assignment.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment.model.CachedStock

@Dao
interface StockDao {
    @Query("SELECT * FROM stock_cache WHERE symbol = :symbol")
    suspend fun getStock(symbol: String): CachedStock?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stock: CachedStock)
}
