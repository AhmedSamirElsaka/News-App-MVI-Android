package com.example.newsapp.presentation.ui.news

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newsapp.domain.models.news.DatabaseArticle
import com.example.newsapp.presentation.ui.NewsUiState
import com.example.newsapp.presentation.ui.Screens
import com.example.newsapp.presentation.ui.SharedViewModel

@SuppressLint("StateFlowValueCalledInComposition")

val categories =
    listOf("business", "entertainment", "general", "health", "science", "sports", "technology")
var selectedCategory = categories.first()

@Composable
fun AllNewsScreen(
    navController: NavHostController,
    newsViewModel: NewsViewModel,
    sharedViewModel: SharedViewModel
) {
    val newsUiState by newsViewModel.allNewsUiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column {
            // Categories Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(8.dp, top = 32.dp)
            ) {
                categories.forEach { category ->
                    Text(
                        text = category,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable {
                                selectedCategory = category
                                newsViewModel.loadNewsByCategory(category) // Trigger loading news by category
                            },
                        color = if (selectedCategory == category) Color.Blue else Color.Black,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                when (newsUiState) {

                    is NewsUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is NewsUiState.Success -> {
                        val articles = (newsUiState as NewsUiState.Success).toData()
                        if (!articles.isNullOrEmpty()) {
                            NewsList(articles, navController, sharedViewModel)
                        }
                    }
                    is NewsUiState.Error -> {
                        Text(
                            text = (newsUiState as NewsUiState.Error).message,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NewsList(
    articles: List<DatabaseArticle>,
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = selectedCategory + " News",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Black
        )
        LazyColumn {
            items(articles) { article ->
                NewsCard(
                    article = article,
                    navController = navController,
                    sharedViewModel = sharedViewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsCard(
    article: DatabaseArticle,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    Card(
        modifier = modifier.padding(16.dp),
        onClick = {
            sharedViewModel.addArticle(article)
            navController.navigate(Screens.SpecificArticleScreen.route)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = article.title!!,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(16.dp),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}