package com.example.newsapp.domain.models.news

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class DatabaseArticle(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String?,
    val url: String,
    val content: String?
)
