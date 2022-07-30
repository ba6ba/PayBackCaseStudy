package com.ba6ba.paybackcasestudy.images.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ba6ba.network.ApiResult
import com.ba6ba.paybackcasestudy.common.Constants
import com.ba6ba.paybackcasestudy.common.default
import javax.inject.Inject

interface ImagePagingSourceProvider {
    fun get(query: String): PagingSource<Int, ImageResponseItem>
}

class DefaultImagePagingSourceProvider @Inject constructor(
    private val imageRepository: ImageRepository
) : ImagePagingSourceProvider {
    override fun get(query: String): PagingSource<Int, ImageResponseItem> {
        return object : PagingSource<Int, ImageResponseItem>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageResponseItem> {
                val loadResult: LoadResult<Int, ImageResponseItem>
                val page = params.key ?: Constants.DEFAULT_PAGE
                loadResult = when (val response = imageRepository.getImages(query, page)) {
                    is ApiResult.Success ->
                        LoadResult.Page(
                            data = response.data.hits.orEmpty(),
                            prevKey = getPreviousKey(page),
                            nextKey = getNextKey(
                                page,
                                response.data.hits?.count().default()
                            )
                        )

                    is ApiResult.Failure ->
                        LoadResult.Error(Throwable(response.error.message))
                }
                return loadResult
            }

            override fun getRefreshKey(state: PagingState<Int, ImageResponseItem>): Int? = null
        }
    }

    private fun getPreviousKey(page: Int): Int? =
        if (Constants.DEFAULT_PAGE == page) null else page

    private fun getNextKey(page: Int, currentFetchedItemsCount: Int): Int? =
        if (currentFetchedItemsCount < Constants.PAGE_LIMIT) null else page.inc()
}