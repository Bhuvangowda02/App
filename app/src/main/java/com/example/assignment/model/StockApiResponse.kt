package com.example.assignment.model

import com.google.gson.annotations.SerializedName

data class StockMetaData(
    @SerializedName("1. Information")
    val information: String,
    @SerializedName("2. Symbol")
    val symbol: String,
    @SerializedName("3. Last Refreshed")
    val lastRefreshed: String,
    @SerializedName("4. Output Size")
    val outputSize: String,
    @SerializedName("5. Time Zone")
    val timeZone: String
)

data class DailyData(
    @SerializedName("1. open")
    val open: String,
    @SerializedName("2. high")
    val high: String,
    @SerializedName("3. low")
    val low: String,
    @SerializedName("4. close")
    val close: String
)

data class StockApiResponse(
    @SerializedName("Meta Data")
    val metaData: StockMetaData,
    @SerializedName("Time Series (Daily)")
    val timeSeriesDaily: Map<String, DailyData>
)

fun StockApiResponse.toStockFromDaily(): Stock {
    val latestEntry = timeSeriesDaily.entries.firstOrNull()
    val dailyData = latestEntry?.value
    return Stock(
        name = metaData.symbol,
        symbol = metaData.symbol,
        open = dailyData?.open?.toDoubleOrNull() ?: 0.0,
        high = dailyData?.high?.toDoubleOrNull() ?: 0.0,
        low = dailyData?.low?.toDoubleOrNull() ?: 0.0,
        close = dailyData?.close?.toDoubleOrNull() ?: 0.0
    )
}
