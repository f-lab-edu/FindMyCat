package com.flab.findmycat.network

import com.flab.findmycat.domain.DetailCats
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.flab.findmycat.domain.Cat
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.thecatapi.com/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

var httpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(httpClient)
    .build()

interface CatsApiService {
    @GET("breeds")
    suspend fun getCats(@Query("page") page: Int, @Query("limit") limit: Int): List<Cat>

    @GET("images/search?breed_id={breed-id}")
    suspend fun getSearchCat(@Query("q") q: String): DetailCats
}

object CatApi {
    val retrofitService: CatsApiService by lazy { retrofit.create(CatsApiService::class.java) }
}