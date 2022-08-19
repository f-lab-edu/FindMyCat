package kr.f.lab.findmycat.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kr.f.lab.findmycat.domain.Cat
import okhttp3.Interceptor
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

val headersInterceptor = Interceptor { chain ->
    with(chain) {
        val newRequest = request().newBuilder()
            .build()
        proceed(newRequest)
    }
}

var httpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(headersInterceptor).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(httpClient)
    .build()

interface CatsApiService {
    @GET("breeds")
    suspend fun getCats(@Query("page") page: Int, @Query("limit") limit: Int): List<Cat>

    @GET("breeds/search")
    suspend fun getSearchCat(@Query("q") q: String): List<Cat>
}

object CatApi {
    val retrofitService: CatsApiService by lazy { retrofit.create(CatsApiService::class.java) }
}

enum class ApiStatus { LOADING, ERROR, DONE }
