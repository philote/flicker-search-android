package com.josephhopson.flickersearch.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlickerSearchResult(
    val title: String,
    val link: String,
    val description: String,
    val modified: String,
    val generator: String,
    @SerialName("items") val images: List<Image>,
)

@Serializable
data class Image(
    val title: String,
    val link: String,
    val media: Media,
    @SerialName("date_taken") val dateTaken: String,
    val description: String,
    val published: String,
    val author: String,
    @SerialName("author_id") val authorId: String,
    val tags: String,
)

@Serializable
data class Media(
    val m: String,
)