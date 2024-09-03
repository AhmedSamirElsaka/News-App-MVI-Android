package com.example.newsapp.presentation.ui

sealed class Screens(val route: String) {
    object AllNewsScreen : Screens("all_news_screen")
    object SpecificArticleScreen : Screens("specific_article_screen")
}