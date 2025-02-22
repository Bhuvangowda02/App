package com.example.assignment.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment.R

// Sample stock transaction data
data class StockTransaction(val stockName: String, val amount: String, val isProfit: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen() {
    val stockTransactions = listOf(
        StockTransaction("Tesla Inc.", "+$2,300", isProfit = true),
        StockTransaction("Amazon", "-$750", isProfit = false),
        StockTransaction("Google", "+$1,100", isProfit = true),
        StockTransaction("Apple", "-$450", isProfit = false),
        StockTransaction("Microsoft", "+$900", isProfit = true),
        StockTransaction("Netflix", "+$1,250", isProfit = true),
        StockTransaction("Intel", "-$300", isProfit = false),
        StockTransaction("AMD", "+$450", isProfit = true),
        StockTransaction("Uber", "-$180", isProfit = false),
        StockTransaction("Airbnb", "+$720", isProfit = true),
        StockTransaction("Oracle", "+$330", isProfit = true),
        StockTransaction("IBM", "-$600", isProfit = false),
        StockTransaction("Boeing", "+$210", isProfit = true),
        StockTransaction("Pfizer", "+$130", isProfit = true),
        StockTransaction("Bristol Myers Squibb", "-$350", isProfit = false)
    )

    Scaffold(

    ) { innerPadding -> // Padding from Scaffold to prevent overlap
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // 🔹 Ensures content starts **below the AppBar**
                .padding(1.dp) // Additional padding for spacing
        ) {
            Text(
                text = "Stock Transactions",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(stockTransactions) { transaction ->
                    StockTransactionItem(transaction)
                }
            }
        }
    }
}

@Composable
fun StockTransactionItem(transaction: StockTransaction) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .animateContentSize()
            .clickable { expanded = !expanded } // Clicking will expand details
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_stock), // Ensure this icon exists in res/drawable
                        contentDescription = "Stock Icon",
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = transaction.stockName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (transaction.isProfit) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward,
                        contentDescription = "Profit/Loss Indicator",
                        tint = if (transaction.isProfit) Color.Green else Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = transaction.amount,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (transaction.isProfit) Color.Green else Color.Red
                    )
                }
            }

            // Expandable section for more details
            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Text(
                        text = "Shares Traded: 50",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Transaction Date: 12th Feb 2024",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
