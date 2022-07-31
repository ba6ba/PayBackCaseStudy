package com.ba6ba.paybackcasestudy.images.data

import androidx.paging.*
import com.ba6ba.network.ApiResult
import com.ba6ba.network.BaseRepository
import com.ba6ba.paybackcasestudy.common.Constants
import com.ba6ba.paybackcasestudy.database.PayBackCaseStudyDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ImageRepository : BaseRepository {

    fun getImages(query: String): Flow<PagingData<ImageResponseItem>>
}

class DefaultImageRepository @Inject constructor(
    private val imagePagingSourceProvider: ImagePagingSourceProvider
) : ImageRepository {
    override fun getImages(query: String): Flow<PagingData<ImageResponseItem>> {
        return Pager(
            config = PagingConfig(Constants.PAGE_LIMIT),
            pagingSourceFactory = {
                imagePagingSourceProvider.get(query)
            }
        ).flow
    }
}