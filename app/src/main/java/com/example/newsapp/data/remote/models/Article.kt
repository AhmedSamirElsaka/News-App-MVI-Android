package com.example.newsapp.data.remote.models

data class Article(
    val articles: List<ArticleX>,
    val status: String,
    val totalResults: Int
)