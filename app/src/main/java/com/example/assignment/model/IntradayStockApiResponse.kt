package com.example.assignment.model

import com.google.gson.annotations.SerializedName

data class IntradayStockMetaData(
    @SerializedName("1. Information")
    val information: String,
    @SerializedName("2. Symbol")
    val symbol: String,
    @SerializedName("3. Last Refreshed")
    val lastRefreshed: String,
    @SerializedName("4. Interval")
    val interval: String,
    @SerializedName("5. Output Size")
    val outputSize: String,
    @SerializedName("6. Time Zone")
    val timeZone: String
)



data class IntradayStockApiResponse(
    @SerializedName("Meta Data")
    val metaData: IntradayStockMetaData,
    @SerializedName("Time Series (5min)")
    val timeSeriesIntraday: Map<String, IntradayData>
)
