package com.ba6ba.paybackcasestudy.images.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageItemDetailUiData(
    val hdImageUrl: String,
    val userName: String,
    val tags: List<String>,
    val likes: String,
    val comments: String,
    val downloads: String
) : Parcelable