package com.example.newsapp.presentation.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.newsapp.domain.models.news.DatabaseArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    var article by mutableStateOf<DatabaseArticle?>(null)
        private set

    fun addArticle(newArticle: DatabaseArticle) {
        article = newArticle
    }
}