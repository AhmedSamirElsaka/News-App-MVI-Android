package com.example.newsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.domain.models.news.DatabaseArticle

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticles(articles: List<DatabaseArticle>)

    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<DatabaseArticle>

    @Query("DELETE FROM articles")
    suspend fun clearAllArticles()
}