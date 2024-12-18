package com.josephhopson.flickersearch.data

import android.util.Log
import com.josephhopson.flickersearch.data.network.FlickerApi
import retrofit2.HttpException
import java.io.IOException

/**
 * A Sealed Interface for the Image API results
 */
sealed interface ImageApiResult {
    data class Success(val images: List<Image>): ImageApiResult
    data object Error: ImageApiResult
}

/**
 * Interface for the Image Repository
 */
interface ImageRepository {
    suspend fun getImageSearchResults(tags: String): ImageApiResult
}

/**
 * Default implementation of the Image Repository
 */
class NetworkImageDataRepository: ImageRepository {
    override suspend fun getImageSearchResults(tags: String): ImageApiResult {
        return try {
            val result = FlickerApi.retrofitService.getFlickerResults(tags)
            if (result.images.isEmpty()) {
                Log.e("FlickerApi", "Image list is empty")
                ImageApiResult.Error
            } else {
                ImageApiResult.Success(result.images)
            }
        } catch (e: IOException) {
            Log.e("FlickerApi:IOException", e.message.toString())
            ImageApiResult.Error
        } catch (e: HttpException) {
            Log.e("FlickerApi:HttpException", e.message.toString())
            ImageApiResult.Error
        }
    }
}