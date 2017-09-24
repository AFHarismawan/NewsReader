package com.harismawan.slapp.data

import com.harismawan.newsreader.data.model.ListSource
import com.harismawan.newsreader.data.model.News
import retrofit2.Call
import retrofit2.http.GET

interface APIHelper {

    @GET("sources")
    fun getAllSource(): Call<ListSource>

    @GET("")
    fun getAllNews(): Call<List<News>>
}