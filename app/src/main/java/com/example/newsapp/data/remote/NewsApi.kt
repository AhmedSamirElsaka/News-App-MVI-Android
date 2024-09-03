package com.example.newsapp.data.remote

import com.example.newsapp.data.remote.models.Article
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApi {

    @GET("category/{category}/{country}.json")
    suspend fun getNews(
        @Path("country") country: String = "us", @Path("category") category: String = "business"
    ): Article
}
