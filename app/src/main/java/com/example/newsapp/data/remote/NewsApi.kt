package com.example.newsapp.data.remote

import com.example.newsapp.data.remote.models.Article
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApi {

//    @GET("category/health/in.json")
//    suspend fun getNews(): Article
    @GET("category/{category}/{country}.json")
    suspend fun getNews(
        @Path("country") country: String = "in", @Path("category") category: String = "business"
    ): Article
}
