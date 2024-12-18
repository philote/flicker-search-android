package com.josephhopson.flickersearch.ui.image

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.josephhopson.flickersearch.data.Image
import java.util.UUID

data class ImageDetailUiState(
    val imageDetails: ImageDetails = ImageDetails()
)

class ImageDetailsViewModel() : ViewModel() {
    var imageDetailsUiState by mutableStateOf(ImageDetailUiState())
        private set
}

data class ImageDetails(
    val id: String = "",
    val imageThumbUrl: String = "",
    val imageUrl: String = "",
    val dateTaken: String = "",
    val published: String = "",
    val author: String = "",
    val tags: String = "",
)

fun Image.toImageDetails(): ImageDetails = ImageDetails(
    id = UUID.randomUUID().toString(),
    imageThumbUrl = media.m,
    imageUrl = getFullImageUrl(media.m),
    dateTaken = dateTaken,
    published = published,
    author = author,
    tags = tags
)

fun getFullImageUrl(thumbUrl: String): String {
    return thumbUrl.replace("_m", "")
}