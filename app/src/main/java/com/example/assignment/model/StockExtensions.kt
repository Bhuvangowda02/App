package com.example.assignment.model

fun Stock.toCachedStock(): CachedStock {
    return CachedStock(
        symbol = this.symbol,
        name = this.name,
        open = this.open,
        high = this.high,
        low = this.low,
        close = this.close,
        lastUpdated = System.currentTimeMillis()
    )
}

fun CachedStock.toStock(): Stock {
    return Stock(
        symbol = this.symbol,
        name = this.name,
        open = this.open,
        high = this.high,
        low = this.low,
        close = this.close
    )
}
