package com.example.assignment.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignment.model.CachedStock

@Database(entities = [CachedStock::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao
}
