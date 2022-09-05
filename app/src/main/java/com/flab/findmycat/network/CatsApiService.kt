package com.flab.findmycat.network

import com.flab.findmycat.domain.Cat
import com.flab.findmycat.domain.Image
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.thecatapi.com/v1/"

private val moshi = Moshi.Builder()
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

    @GET("images/search")
    suspend fun getSearchCats(@Query("breed_id") breedId: String): List<Image>
}

object CatApi {
    val retrofitService: CatsApiService by lazy { retrofit.create(CatsApiService::class.java) }
}
