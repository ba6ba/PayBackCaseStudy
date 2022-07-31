package com.ba6ba.paybackcasestudy.images.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageDetailArgsData(
    val previewUrl: String,
    val hdImageUrl: String,
    val userName: String,
    val tags: String,
    val likes: Long,
    val comments: Long,
    val downloads: Long
) : Parcelable