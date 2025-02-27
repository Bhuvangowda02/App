package com.example.assignment.network

import com.example.assignment.model.IntradayStockApiResponse
import com.example.assignment.model.RealtimeStockResponse
import com.example.assignment.model.StockApiResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageService {
    // Daily data endpoint (for listing stocks)
    @GET("query")
    suspend fun getDailyStockData(
        @Query("function") function: String = "TIME_SERIES_DAILY",
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String,
        @Query("outputsize") outputSize: String = "compact"
    ): Response<StockApiResponse>

    // Realtime data endpoint using GLOBAL_QUOTE (if needed)
    @GET("query")
    suspend fun getRealtimeStockData(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String
    ): Response<RealtimeStockResponse>

    // Intraday data endpoint using TIME_SERIES_INTRADAY
    @GET("query")
    suspend fun getIntradayStockData(
        @Query("function") function: String = "TIME_SERIES_INTRADAY",
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String,
        @Query("interval") interval: String = "5min",
        @Query("outputsize") outputSize: String = "compact"
    ): Response<IntradayStockApiResponse>

    companion object {
        fun create(): AlphaVantageService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.alphavantage.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(AlphaVantageService::class.java)
        }
    }
}
