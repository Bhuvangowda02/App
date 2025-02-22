package com.example.assignment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.network.RSSItem
import com.example.assignment.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel : ViewModel() {
    private val _newsList = MutableStateFlow<List<RSSItem>>(emptyList())
    val newsList: StateFlow<List<RSSItem>> = _newsList

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            try {
                val response: Response<com.example.assignment.network.RSSFeed> =
                    RetrofitInstance.api.getStockNews("https://news.google.com/rss/search?q=Indian+Stock+Market&hl=en-IN&gl=IN&ceid=IN:en")

                if (response.isSuccessful && response.body() != null) {
                    val rssFeed = response.body()!!
                    _newsList.value = rssFeed.channel?.articles ?: emptyList()
                    Log.d("NewsViewModel", "Fetched ${rssFeed.channel?.articles?.size ?: 0} articles")
                } else {
                    Log.e("NewsViewModel", "API Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("NewsViewModel", "Error fetching news: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }
}
