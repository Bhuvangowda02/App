package com.example.assignment.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.assignment.model.Stock

@Composable
fun StockDetailScreen(stock: Stock, onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Stock Detail", style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)
        Text(text = "Name: ${stock.name}")
        Text(text = "Symbol: ${stock.symbol}")
        Text(text = "Open: ${stock.open}")
        Text(text = "High: ${stock.high}")
        Text(text = "Low: ${stock.low}")
        Text(text = "Close: ${stock.close}")
        Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) {
            Text("Back")
        }
    }
}
