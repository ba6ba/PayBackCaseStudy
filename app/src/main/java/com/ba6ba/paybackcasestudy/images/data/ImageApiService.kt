package com.ba6ba.paybackcasestudy.images.data

import com.ba6ba.paybackcasestudy.common.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApiService {

    @GET(".")
    suspend fun getImages(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("key") key: String = Constants.API_KEY,
        @Query("per_page") perPage: Int = Constants.PAGE_LIMIT
    ): ImageListingResponse
}