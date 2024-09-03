package com.example.newsapp.presentation.ui.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.mapper.toCachedArticles
import com.example.newsapp.domain.models.news.DatabaseArticle
import com.example.newsapp.domain.repository.INewsRepository
import com.example.newsapp.domain.util.FetchActions
import com.example.newsapp.presentation.ui.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repo: INewsRepository
) : ViewModel() {
    private var _allNewsUiState: MutableStateFlow<NewsUiState<List<DatabaseArticle>>> =
        MutableStateFlow(NewsUiState.Loading)

    val allNewsUiState = _allNewsUiState.asStateFlow()


    fun getNewsFromApi() {
        viewModelScope.launch {

            _allNewsUiState.value = NewsUiState.Loading

            when (val result = repo.fetchData()) {

                is FetchActions.Success -> {
                    _allNewsUiState.value = NewsUiState.Success(result.data!!)
                }

                is FetchActions.Error -> {
                    Log.i("hello", "getNewsFromApi: " + result.data)
                    _allNewsUiState.value = NewsUiState.Error(result.message.toString())
                }
            }
        }
    }

    fun loadNewsByCategory(category: String) {
        viewModelScope.launch {
            _allNewsUiState.value = NewsUiState.Loading
            when (val result = repo.fetchDataByCategory(category)) {

                is FetchActions.Success -> {
                    _allNewsUiState.value =
                        NewsUiState.Success(result.data?.articles?.toCachedArticles()!!)
                }

                is FetchActions.Error -> {
                    _allNewsUiState.value = NewsUiState.Error(result.message.toString())
                }
            }
        }
    }
}