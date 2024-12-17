package com.josephhopson.flickersearch.data

import android.util.Log
import com.josephhopson.flickersearch.data.network.FlickerApi
import retrofit2.HttpException
import java.io.IOException

sealed interface ImageApiResult {
    data class Success(val images: List<Image>): ImageApiResult
    data object Error: ImageApiResult
}

interface ImageRepository {
    suspend fun getImageSearchResults(tags: String): ImageApiResult
}

class NetworkImageDataRepository(): ImageRepository {
    override suspend fun getImageSearchResults(tags: String): ImageApiResult {
        return try {
            val result = FlickerApi.retrofitService.getFlickerResults(tags)
            Log.d("FlickerApi", result.toString())
            // TODO check images for content and send error when empty
            ImageApiResult.Success(result.images)
        } catch (e: IOException) {
            Log.e("FlickerApi:IOException", e.message.toString())
            ImageApiResult.Error
        } catch (e: HttpException) {
            Log.e("FlickerApi:HttpException", e.message.toString())
            ImageApiResult.Error
        }
    }
}