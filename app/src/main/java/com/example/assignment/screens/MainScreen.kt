@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.assignment.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun MainScreen() {
    var selectedStock by remember { mutableStateOf<Stock?>(null) }
    var searchText by remember { mutableStateOf("") }

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
                textStyle = MaterialTheme.typography.bodyLarge,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedStock == null) {
                StockList(searchText) { stock ->
                    selectedStock = stock
                }
            } else {
                Column {
                    StockDetailScreen(stock = selectedStock!!) {
                        selectedStock = null
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    StockGraph(stock = selectedStock!!) // ✅ Show Stock Graph when a stock is selected
                }
            }
        }
    }
}

@Composable
fun StockList(searchText: String, onStockClick: (Stock) -> Unit) {
    val stocks = listOf(
        Stock("Apple Inc.", "AAPL", 185.62, 2.45),
        Stock("Tesla", "TSLA", 254.31, -1.15),
        Stock("Amazon", "AMZN", 144.52, 3.78),
        Stock("Google", "GOOGL", 2753.50, 1.25),
        Stock("Microsoft", "MSFT", 289.99, -0.89),
        Stock("Nvidia", "NVDA", 345.23, 5.12),
        Stock("Intel", "INTC", 45.76, -0.45),
        Stock("AMD", "AMD", 112.45, 3.20)
    )

    val filteredStocks = stocks.filter {
        it.name.contains(searchText, ignoreCase = true) || it.symbol.contains(searchText, ignoreCase = true)
    }

    LazyColumn {
        items(filteredStocks) { stock ->
            StockItem(stock, onStockClick)
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
                Text(text = stock.symbol, fontSize = 14.sp, color = MaterialTheme.colorScheme.secondary)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "$${stock.price}", fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                Text(text = "${if (stock.change >= 0) "+" else ""}${stock.change}%", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun StockDetailScreen(stock: Stock, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stock.name, fontSize = 24.sp, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Symbol: ${stock.symbol}", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
        Text(text = "Current Price: $${stock.price}", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
        Text(
            text = "Change: ${stock.change}%",
            fontSize = 20.sp,
            color = if (stock.change >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) {
            Text("Back to Stock List")
        }
    }
}

@Composable
fun StockGraph(stock: Stock) {
    val prices = generateFakePriceData(stock.price)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Stock Price Trend (Last 7 Days)", modifier = Modifier.padding(8.dp))

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(8.dp)
        ) {
            val maxPrice = prices.maxOrNull() ?: stock.price
            val minPrice = prices.minOrNull() ?: stock.price
            val priceRange = maxPrice - minPrice

            if (priceRange == 0.0) return@Canvas // Prevent division by zero

            for (i in 1 until prices.size) {
                val startX = (i - 1) * (size.width / (prices.size - 1))
                val endX = i * (size.width / (prices.size - 1))
                val startY = size.height - ((prices[i - 1] - minPrice) / priceRange) * size.height
                val endY = size.height - ((prices[i] - minPrice) / priceRange) * size.height

                drawLine(
                    color = Color.Blue,
                    start = Offset(startX.toFloat(), startY.toFloat()),
                    end = Offset(endX.toFloat(), endY.toFloat()),
                    strokeWidth = 5f,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

// Simulates random stock price data based on the current price
fun generateFakePriceData(currentPrice: Double): List<Double> {
    val random = Random(currentPrice.toLong()) // Seed with stock price for consistency
    return List(7) { currentPrice + random.nextDouble(-10.0, 10.0) }
}

data class Stock(
    val name: String,
    val symbol: String,
    val price: Double,
    val change: Double
)
