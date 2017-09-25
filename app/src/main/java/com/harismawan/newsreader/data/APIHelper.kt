package com.harismawan.slapp.data

import com.harismawan.newsreader.data.model.ListSource
import com.harismawan.newsreader.data.model.Article
import com.harismawan.newsreader.data.model.ListArticle
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIHelper {

    @GET("sources")
    fun getAllSource(): Call<ListSource>

    @GET("articles")
    fun getAllArticle(@Query("source") source: String, @Query("apiKey") apiKey: String): Call<ListArticle>
}