package com.ba6ba.paybackcasestudy.images.data

import com.ba6ba.network.ApiResult
import com.ba6ba.network.NetworkDataSource
import javax.inject.Inject

interface ImageNetworkDataSource : NetworkDataSource {
    suspend fun getImages(query: String, page: Int): ApiResult<ImageListingResponse>
}

class DefaultImageNetworkDataSource @Inject constructor(
    private val imageApiService: ImageApiService
) : ImageNetworkDataSource {
    override suspend fun getImages(query: String, page: Int): ApiResult<ImageListingResponse> {
        return execute { imageApiService.getImages(query, page) }
    }
}