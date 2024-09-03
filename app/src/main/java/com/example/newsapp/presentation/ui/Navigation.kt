package com.example.newsapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.presentation.ui.news.AllNewsScreen
import com.example.newsapp.presentation.ui.news.NewsViewModel

@Composable
fun Navigation(newsViewModel: NewsViewModel, sharedViewModel: SharedViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.AllNewsScreen.route) {
        composable(route = Screens.AllNewsScreen.route) {
            AllNewsScreen(navController = navController, newsViewModel, sharedViewModel)
        }
        composable(route = Screens.SpecificArticleScreen.route) {
            SpecificArticleScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
    }
}
