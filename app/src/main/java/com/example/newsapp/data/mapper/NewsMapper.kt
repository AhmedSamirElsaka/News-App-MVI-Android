package com.example.newsapp.data.mapper

import com.example.newsapp.data.remote.models.ArticleX
import com.example.newsapp.domain.models.news.DatabaseArticle


fun List<ArticleX>.toCachedArticles(): List<DatabaseArticle> {
    return this.map { article ->
        DatabaseArticle(

            title = article.title!!,
            description = article.description,
            url = article.url!!,

            content = article.content
        )
    }
}