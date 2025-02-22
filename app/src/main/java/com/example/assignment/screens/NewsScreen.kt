@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.assignment.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignment.network.RSSItem
import com.example.assignment.viewmodel.NewsViewModel


@Composable
fun NewsScreen(newsViewModel: NewsViewModel = viewModel()) {
    val newsList by newsViewModel.newsList.collectAsState()

    Scaffold(

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(1.dp),
        ) {
            if (newsList.isEmpty()) {
                Text(
                    text = "No news available. Please check your internet connection.",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(newsList) { article ->
                        NewsItem(article)
                    }
                }
            }
        }
    }
}

@Composable
fun NewsItem(article: RSSItem) {
    val uriHandler = LocalUriHandler.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { uriHandler.openUri(article.url) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = article.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            article.description?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    maxLines = 2
                )
            }
        }
    }
}
