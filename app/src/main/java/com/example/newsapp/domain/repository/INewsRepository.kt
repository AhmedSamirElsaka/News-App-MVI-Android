package com.example.newsapp.domain.repository

import com.example.newsapp.data.remote.models.Article
import com.example.newsapp.domain.models.news.DatabaseArticle
import com.example.newsapp.domain.util.FetchActions


interface INewsRepository {
    suspend fun fetchData():FetchActions<List<DatabaseArticle>>
    suspend fun fetchDataByCategory(category: String): FetchActions<Article>
}