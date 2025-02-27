
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.example.assignment.model.Stock

import kotlin.random.Random

@Composable
fun StockGraph(stock: Stock) {
    val prices = generateCandlestickData(stock.close)


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = "Stock Candlestick Chart (Last 7 Days)", modifier = Modifier.padding(bottom = 8.dp))

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp)
            ) {
                val maxPrice = prices.maxOf { it.high }
                val minPrice = prices.minOf { it.low }
                val priceRange = maxPrice - minPrice

                val candleWidth = size.width / prices.size.toFloat() * 0.6f  // Adjust width of candles

                for ((index, data) in prices.withIndex()) {
                    val x = index * (size.width / (prices.size - 1))

                    // Scale price values to fit canvas
                    val openY = size.height - ((data.open - minPrice) / priceRange) * size.height
                    val closeY = size.height - ((data.close - minPrice) / priceRange) * size.height
                    val highY = size.height - ((data.high - minPrice) / priceRange) * size.height
                    val lowY = size.height - ((data.low - minPrice) / priceRange) * size.height

                    val candleColor = if (data.close > data.open) Color.Green else Color.Red

                    // Draw the thin wick line
                    drawLine(
                        color = candleColor,
                        start = Offset(x, highY.toFloat()),
                        end = Offset(x, lowY.toFloat()),
                        strokeWidth = 4f,
                        cap = StrokeCap.Round
                    )

                    // Draw the candlestick body
                    drawRect(
                        color = candleColor,
                        topLeft = Offset(x - candleWidth / 2, minOf(openY, closeY).toFloat()),
                        size = androidx.compose.ui.geometry.Size(
                            candleWidth,
                            kotlin.math.abs(closeY - openY).toFloat()
                        )
                    )
                }
            }
        }
    }
}

// 🔹 Generate Candlestick Data
data class CandlestickData(
    val open: Double,
    val close: Double,
    val high: Double,
    val low: Double
)

fun generateCandlestickData(currentPrice: Double): List<CandlestickData> {
    val random = Random(System.currentTimeMillis())

    return List(7) {
        val open = currentPrice + random.nextDouble(-5.0, 5.0)
        val close = open + random.nextDouble(-5.0, 5.0)
        val high = maxOf(open, close) + random.nextDouble(0.0, 5.0)
        val low = minOf(open, close) - random.nextDouble(0.0, 5.0)

        CandlestickData(open, close, high, low)
    }
}
