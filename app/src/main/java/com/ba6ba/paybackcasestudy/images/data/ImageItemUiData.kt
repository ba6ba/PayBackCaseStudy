package com.ba6ba.paybackcasestudy.images.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageItemUiData(
    val imageUrl: String,
    val userName: String,
    val tags: List<String>
) : Parcelable