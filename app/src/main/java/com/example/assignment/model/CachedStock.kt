package com.example.assignment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_cache")
data class CachedStock(
    @PrimaryKey val symbol: String,
    val name: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val lastUpdated: Long
)
