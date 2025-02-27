package com.example.assignment.model

import com.google.gson.annotations.SerializedName

data class RealtimeStockResponse(
    @SerializedName("Global Quote")
    val globalQuote: GlobalQuote?
)

data class GlobalQuote(
    @SerializedName("01. symbol")
    val symbol: String,
    @SerializedName("02. open")
    val open: String,
    @SerializedName("03. high")
    val high: String,
    @SerializedName("04. low")
    val low: String,
    @SerializedName("05. price")
    val price: String,
    @SerializedName("09. change")
    val change: String,
    @SerializedName("10. change percent")
    val changePercent: String
)

fun RealtimeStockResponse.toStock(): Stock {
    val quote = globalQuote
    return if (quote != null) {
        Stock(
            name = quote.symbol,
            symbol = quote.symbol,
            open = quote.open.toDoubleOrNull() ?: 0.0,
            high = quote.high.toDoubleOrNull() ?: 0.0,
            low = quote.low.toDoubleOrNull() ?: 0.0,
            close = quote.price.toDoubleOrNull() ?: 0.0
        )
    } else {
        Stock(name = "Unknown", symbol = "Unknown", open = 0.0, high = 0.0, low = 0.0, close = 0.0)
    }
}
