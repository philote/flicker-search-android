package com.josephhopson.flickersearch.ui.image

import android.annotation.SuppressLint
import android.os.Parcelable
import com.josephhopson.flickersearch.data.Image
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Parcelize
data class ImageDetails(
    val imageThumbUrl: String = "",
    val imageUrl: String = "",
    val dateTaken: String = "",
    val published: String = "",
    val author: String = "",
    val tags: String = "",
) : Parcelable

fun Image.toImageDetails(): ImageDetails = ImageDetails(
    imageThumbUrl = media.m,
    imageUrl = getFullImageUrl(media.m),
    dateTaken = formatDateString(dateTaken),
    published = formatDateString(published),
    author = author,
    tags = tags
)

fun getFullImageUrl(thumbUrl: String): String {
    return thumbUrl.replace("_m", "")
}

@SuppressLint("NewApi")
fun formatDateString(dateString: String): String {
    val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val zonedDateTime = ZonedDateTime.parse(dateString, inputFormatter)
    val outputFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a")
    return zonedDateTime.format(outputFormatter)
}