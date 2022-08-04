package com.ba6ba.paybackcasestudy.images.data

import com.ba6ba.paybackcasestudy.images.domain.Images

sealed class ImageFetchResult {
    data class Data(val data: List<Images>) : ImageFetchResult()
    object Empty : ImageFetchResult()
    data class Error(val message: String) : ImageFetchResult()
}