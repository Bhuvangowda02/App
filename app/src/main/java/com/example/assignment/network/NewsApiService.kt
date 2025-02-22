package com.example.assignment.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

// ✅ API Constants for Google News RSS
object ApiConstants {
    const val BASE_URL = "https://news.google.com/rss/search?q="
}

// ✅ Define API Service for Google News RSS
interface NewsApiService {
    @GET
    suspend fun getStockNews(@Url url: String): Response<RSSFeed>
}

// ✅ Retrofit Instance for Google News RSS
object RetrofitInstance {
    val api: NewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create()) // ✅ Use XML Converter for RSS
            .build()
            .create(NewsApiService::class.java)
    }
}

// ✅ Data Classes for Parsing XML from Google News RSS
@org.simpleframework.xml.Root(name = "rss", strict = false)
data class RSSFeed(
    @org.simpleframework.xml.Element(name = "channel")
    val channel: RSSChannel? = null
)

@org.simpleframework.xml.Root(name = "channel", strict = false)
data class RSSChannel(
    @org.simpleframework.xml.ElementList(inline = true, name = "item")
    val articles: List<RSSItem>? = null
)

@org.simpleframework.xml.Root(name = "item", strict = false)
data class RSSItem(
    @org.simpleframework.xml.Element(name = "title")
    val title: String = "",

    @org.simpleframework.xml.Element(name = "link")
    val url: String = "",

    @org.simpleframework.xml.Element(name = "description", required = false)
    val description: String? = null
)
