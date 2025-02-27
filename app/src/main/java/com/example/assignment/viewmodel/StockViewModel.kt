package com.example.assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.model.Stock
import com.example.assignment.model.toStockFromDaily
import com.example.assignment.network.AlphaVantageService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StockViewModel : ViewModel() {
    private val _stocks = MutableStateFlow<List<Stock>>(emptyList())
    val stocks = _stocks.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val apiService = AlphaVantageService.create()

    fun fetchStockData(symbol: String, apiKey: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getDailyStockData(symbol = symbol, apikey = apiKey)
                if (response.isSuccessful) {
                    response.body()?.let { stockData ->
                        // Convert the daily API response to a Stock using the toStockFromDaily extension function.
                        val stock = stockData.toStockFromDaily()
                        _stocks.value = listOf(stock)
                    }
                } else {
                    println("Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
