package com.josephhopson.flickersearch.data.network

import com.josephhopson.flickersearch.data.FlickerSearchResult
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_FLICKER_URL = "https://api.flickr.com/services/"
// Don't blow up if the API adds extra stuff
private val retroJson = Json { ignoreUnknownKeys = true }
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_FLICKER_URL)
    .addConverterFactory(retroJson.asConverterFactory("application/json; charset=UTF8".toMediaType()))
    .build()

interface ImageApiService {
    @GET("feeds/photos_public.gne?format=json&nojsoncallback=1")
    suspend fun getFlickerResults(@Query("tags") tags: String): FlickerSearchResult
}

object FlickerApi {
    val retrofitService: ImageApiService by lazy {
        retrofit.create(ImageApiService::class.java)
    }
}