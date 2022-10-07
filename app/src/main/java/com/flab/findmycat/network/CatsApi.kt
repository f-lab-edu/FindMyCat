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

interface CatsApi {
    @GET("breeds")
    suspend fun getCats(@Query("page") page: Int, @Query("limit") limit: Int): List<Cat>

    @GET("images/search")
    suspend fun getSearchCats(
        @Query("breed_id") breedId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<Image>

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}

fun provideCatApi(retrofit: Retrofit): CatsApi = retrofit.create(CatsApi::class.java)

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BASIC
    return logger
}

fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()
}




