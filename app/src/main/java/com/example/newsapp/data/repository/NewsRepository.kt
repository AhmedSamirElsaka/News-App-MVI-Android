package com.example.newsapp.data.repository

import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.data.mapper.toCachedArticles
import com.example.newsapp.data.remote.models.Article
import com.example.newsapp.data.remote.NewsApi
import com.example.newsapp.domain.models.news.DatabaseArticle
import com.example.newsapp.domain.repository.INewsRepository
import com.example.newsapp.domain.util.FetchActions
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val api: NewsApi, private val newsDao: NewsDao
) : INewsRepository {
    override suspend fun fetchData(): FetchActions<List<DatabaseArticle>> {

        val cachedNews = newsDao.getAllArticles()

        if (cachedNews.isNotEmpty()) {
            return FetchActions.Success(data = cachedNews)
        }

        return try {
            val apiNews = FetchActions.Success(data = (api.getNews()).articles.toCachedArticles())
            apiNews.data?.let { news ->
                newsDao.clearAllArticles()
                newsDao.insertAllArticles(news)
            }
            apiNews
        } catch (e: Exception) {
            FetchActions.Error(e.message!!)
        }
    }

    override suspend fun fetchDataByCategory(category: String): FetchActions<Article> {
        return try {
            val result = FetchActions.Success(data = api.getNews(category = category))
            result
        } catch (e: Exception) {
            FetchActions.Error(e.message!!)
        }
    }
}
