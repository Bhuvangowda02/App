package com.example.assignment.model

fun IntradayStockApiResponse.toStockFromIntraday(): Stock? {
    // If the map is null or empty, return null
    val entries = timeSeriesIntraday ?: return null
    val latestEntry = entries.entries.firstOrNull() ?: return null
    val intradayData = latestEntry.value
    return Stock(
        name = metaData.symbol,
        symbol = metaData.symbol,
        open = intradayData.open.toDoubleOrNull() ?: 0.0,
        high = intradayData.high.toDoubleOrNull() ?: 0.0,
        low = intradayData.low.toDoubleOrNull() ?: 0.0,
        close = intradayData.close.toDoubleOrNull() ?: 0.0
    )
}
