package com.ba6ba.paybackcasestudy.images.data

import com.ba6ba.network.ApiResult
import com.ba6ba.network.BaseRepository
import javax.inject.Inject

interface ImageNetworkRepository : BaseRepository {
    suspend fun getImages(query: String, page: Int): ApiResult<ImageListingResponse>
}

class DefaultImageNetworkRepository @Inject constructor(
    private val imageApiService: ImageApiService
) : ImageNetworkRepository {
    override suspend fun getImages(query: String, page: Int): ApiResult<ImageListingResponse> {
        return execute { imageApiService.getImages(query, page) }
    }
}