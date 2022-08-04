package com.ba6ba.paybackcasestudy.images.data

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ImageListingResponse(
    val hits: List<ImageResponseItem>?,
    val total: Int?,
    val totalHits: Int?
)

data class ImageResponseItem(
    val id: Long?,
    val pageURL: String?,
    val type: String?,
    val tags: String?,
    val previewURL: String?,
    val previewWidth: Int?,
    val previewHeight: Int?,
    @SerializedName("webformatURL")
    val webFormatURL: String?,
    @SerializedName("webformatWidth")
    val webFormatWidth: Int?,
    @SerializedName("webformatHeight")
    val webFormatHeight: Int?,
    val largeImageURL: String?,
    val fullHDURL: String?,
    val imageURL: String?,
    val imageWidth: Int?,
    val imageHeight: Int?,
    val imageSize: Long?,
    val views: Long?,
    val downloads: Long?,
    val likes: Long?,
    val comments: Long?,
    @SerializedName("user_id")
    val userId: Long?,
    val user: String?,
    val userImageURL: String?
)