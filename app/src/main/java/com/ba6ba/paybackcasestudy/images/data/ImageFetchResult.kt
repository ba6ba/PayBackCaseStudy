package com.ba6ba.paybackcasestudy.images.data

sealed class ImageFetchResult {
    data class Data(val data: List<ImageResponseItem>) : ImageFetchResult()
    object Empty : ImageFetchResult()
    data class Error(val message: String) : ImageFetchResult()
}