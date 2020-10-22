package com.jahk.myarticlesviewer.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface IArticlesService {

    @GET("search_by_date")
    fun getArticles(@Query("query") query: String) : Deferred<MainResponse>

}

object ArticlesNetwork {

    private const val BASE_URL = "http://hn.algolia.com/api/v1/"

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val articles = retrofit.create(IArticlesService::class.java)

}