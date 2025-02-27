package com.example.assignment.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment.model.Stock
import com.example.assignment.model.toStockFromDaily
import com.example.assignment.model.toStockFromIntraday
import com.example.assignment.network.AlphaVantageService
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Response

@Composable
fun MainScreen(alphaVantageService: AlphaVantageService) {
    val context = LocalContext.current

    var selectedStock by remember { mutableStateOf<Stock?>(null) }
    var searchText by remember { mutableStateOf("") }
    var stockDetails by remember { mutableStateOf<Stock?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // In-memory cache for API data: Map symbol -> Pair(stock, timestamp)
    val realtimeCache = remember { mutableStateMapOf<String, Pair<Stock, Long>>() }
    // Cache validity duration: 3 minutes (adjust as needed)
    val cacheDuration = 3 * 60 * 1000L

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Search Stocks") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedStock == null) {
                StockList(searchText, alphaVantageService) { stock ->
                    selectedStock = stock
                    isLoading = true
                    errorMessage = null

                    coroutineScope.launch {
                        try {
                            // 1. Try to load cached data from SharedPreferences
                            val cachedStock = getCachedStock(context, stock.symbol, cacheDuration)
                            if (cachedStock != null) {
                                stockDetails = cachedStock
                            }
                            // 2. Check the in-memory cache before making an API call
                            val currentTime = System.currentTimeMillis()
                            val cachedRealtime = realtimeCache[stock.symbol]
                            if (cachedRealtime != null && (currentTime - cachedRealtime.second < cacheDuration)) {
                                stockDetails = cachedRealtime.first
                                isLoading = false
                            } else {
                                // 3. Fetch intraday data from the API using TIME_SERIES_INTRADAY endpoint
                                val response: Response<com.example.assignment.model.IntradayStockApiResponse> =
                                    alphaVantageService.getIntradayStockData(
                                        symbol = stock.symbol,
                                        apikey = "YOUR_API_KEY" // Replace with your API key
                                    )
                                if (response.isSuccessful) {
                                    response.body()?.toStockFromIntraday()?.let { freshStock ->
                                        stockDetails = freshStock
                                        // Update both caches: in-memory and SharedPreferences
                                        realtimeCache[stock.symbol] = Pair(freshStock, currentTime)
                                        saveCachedStock(context, freshStock)
                                    } ?: run {
                                        errorMessage = "No data available for ${stock.symbol}"
                                    }
                                } else {
                                    errorMessage = "Error: ${response.message()}"
                                }
                            }
                        } catch (e: Exception) {
                            errorMessage = "Error fetching stock data: ${e.localizedMessage}"
                        } finally {
                            isLoading = false
                        }
                    }
                }
            } else {
                Column {
                    if (isLoading) {
                        CircularProgressIndicator()
                    } else if (errorMessage != null) {
                        Text(text = errorMessage!!, color = Color.Red)
                    } else if (stockDetails != null) {
                        StockDetailScreen(stock = stockDetails!!) {
                            selectedStock = null
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StockList(
    searchText: String,
    alphaVantageService: AlphaVantageService,
    onStockClick: (Stock) -> Unit
) {
    val stocks = listOf("AAPL", "TSLA", "AMZN", "IBM", "SHOP.TRT", "RELIANCE.BSE", "GPV.TRV", "MBG.DEX")
    val coroutineScope = rememberCoroutineScope()
    val stockData = remember { mutableStateMapOf<String, Stock>() }

    LaunchedEffect(Unit) {
        stocks.forEach { symbol ->
            coroutineScope.launch {
                try {
                    val response: Response<com.example.assignment.model.StockApiResponse> =
                        alphaVantageService.getDailyStockData(
                            symbol = symbol,
                            apikey = "OOXZ347U9E2I0AGJ" // Replace with your API key
                        )
                    if (response.isSuccessful) {
                        response.body()?.toStockFromDaily()?.let { stock ->
                            stockData[symbol] = stock
                        }
                    }
                } catch (e: Exception) {
                    // Optionally log exception details here
                }
            }
        }
    }

    LazyColumn {
        items(stocks) { symbol ->
            stockData[symbol]?.let { stock ->
                StockItem(stock, onStockClick)
            }
        }
    }
}

@Composable
fun StockItem(stock: Stock, onStockClick: (Stock) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onStockClick(stock) },
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = stock.name, fontSize = 18.sp)
                Text(
                    text = stock.symbol,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

// --- SharedPreferences Caching Functions ---

fun saveCachedStock(context: Context, stock: Stock) {
    val prefs = context.getSharedPreferences("stock_cache", Context.MODE_PRIVATE)
    val editor = prefs.edit()
    val json = Gson().toJson(stock)
    editor.putString(stock.symbol, json)
    editor.putLong("${stock.symbol}_timestamp", System.currentTimeMillis())
    editor.apply()
}

fun getCachedStock(context: Context, symbol: String, cacheDuration: Long): Stock? {
    val prefs = context.getSharedPreferences("stock_cache", Context.MODE_PRIVATE)
    val timestamp = prefs.getLong("${symbol}_timestamp", 0)
    return if (System.currentTimeMillis() - timestamp < cacheDuration) {
        val json = prefs.getString(symbol, null)
        if (json != null) {
            Gson().fromJson(json, Stock::class.java)
        } else {
            null
        }
    } else {
        null
    }
}
