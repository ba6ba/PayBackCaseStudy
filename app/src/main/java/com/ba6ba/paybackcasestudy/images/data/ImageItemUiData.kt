package com.ba6ba.paybackcasestudy.images.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageItemUiData(
    val imageUrl: String,
    val userName: String,
    val tags: List<String>,
    val metadata: Metadata
) : Parcelable {

    @Parcelize
    data class Metadata(
        val hdImageUrl: String,
        val tags: String,
        val likes: Long,
        val comments: Long,
        val downloads: Long
    ) : Parcelable
}