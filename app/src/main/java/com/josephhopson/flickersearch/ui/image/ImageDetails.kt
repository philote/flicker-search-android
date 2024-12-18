package com.josephhopson.flickersearch.ui.image

import android.annotation.SuppressLint
import android.os.Parcelable
import com.josephhopson.flickersearch.data.Image
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/*
    NOTE: this was gonna be a viewmodel, but then I switch to the "fancy" new
        ListDetailPaneScaffold and it seemed less useful. For any app that has real navigation needs
        I would probably switch back to a regular navigation component and have viewModels backing
        all the major screens (or major UI functionality).
 */

/**
 * [ImageDetails] is a class that contains a UI friendly version of the information about an image
 * from the Flicker feed API.
 *
 * @param imageThumbUrl the URL of the image thumbnail
 * @param imageUrl the URL of the full image
 * @param dateTaken the date the image was taken
 * @param published the date the image was published
 * @param author the author of the image
 * @param tags the tags associated with the image
 */
@Parcelize
data class ImageDetails(
    val title: String = "",
    val widthHeight: Pair<Int, Int>? = null,
    val imageThumbUrl: String = "",
    val imageUrl: String = "",
    val dateTaken: String = "",
    val published: String = "",
    val author: String = "",
    val tags: String = "",
) : Parcelable

/**
 * Transform the repo image data structures to the UI image data structures
 */
fun Image.toImageDetails(): ImageDetails = ImageDetails(
    title = title,
    widthHeight = extractDimensions(description),
    imageThumbUrl = media.m,
    imageUrl = getFullImageUrl(media.m),
    dateTaken = formatDateString(dateTaken),
    published = formatDateString(published),
    author = author,
    tags = tags
)

/**
 * Get the full image URL from the thumbnail URL
 */
fun getFullImageUrl(thumbUrl: String): String {
    return thumbUrl.replace("_m", "")
}

/**
 * Format the date string to a more user friendly format
 */
@SuppressLint("NewApi")
fun formatDateString(dateString: String): String {
    val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val zonedDateTime = ZonedDateTime.parse(dateString, inputFormatter)
    val outputFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a")
    return zonedDateTime.format(outputFormatter)
}

/**
 * Extract the width and height of the image from the description HTML
 * NOTE: Yeah, I forgot how I did this 30 seconds after it worked,
 *  regex and I have a fraught relationship...
 */
fun extractDimensions(html: String): Pair<Int, Int>? {
    val regex = """<img[^>]*\swidth\s*=\s*"(\d+)"[^>]*\sheight\s*=\s*"(\d+)"""".toRegex()
    val matchResult = regex.find(html)

    return if (matchResult != null) {
        val (width, height) = matchResult.destructured
        width.toInt() to height.toInt()
    } else {
        null
    }
}