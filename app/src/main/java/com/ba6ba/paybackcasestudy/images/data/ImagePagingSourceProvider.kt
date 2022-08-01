package com.ba6ba.paybackcasestudy.images.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ba6ba.network.ApiResult
import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.common.*
import com.ba6ba.paybackcasestudy.database.ImagesMetadata
import com.ba6ba.paybackcasestudy.database.PayBackCaseStudyDatabase
import javax.inject.Inject

interface ImagePagingSourceProvider {
    fun get(query: String): PagingSource<Int, ImageResponseItem>
}

class DefaultImagePagingSourceProvider @Inject constructor(
    private val imageNetworkRepository: ImageNetworkRepository,
    private val stringsResourceManager: StringsResourceManager,
    private val localDataProvider: LocalDataProvider
) : ImagePagingSourceProvider {

    override fun get(query: String): PagingSource<Int, ImageResponseItem> {
        return object : PagingSource<Int, ImageResponseItem>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageResponseItem> {
                val loadResult: LoadResult<Int, ImageResponseItem>
                val page = params.key ?: Constants.DEFAULT_PAGE
                val lastFetchedPage = localDataProvider.getLastFetchedPage(query)
                val pageFetchState: PageFetchState =
                    if (page < lastFetchedPage) {
                        PageFetchState.Database
                    } else if (page == lastFetchedPage && getListFromDatabase().isNotEmpty()) {
                        PageFetchState.Database
                    } else {
                        PageFetchState.Remote
                    }

                loadResult = when (pageFetchState) {
                    is PageFetchState.Database -> getLoadResultForDatabase(page)
                    is PageFetchState.Remote -> getLoadResultForRemote(query, page)
                }
                return loadResult
            }

            override fun getRefreshKey(state: PagingState<Int, ImageResponseItem>): Int? = null
        }
    }

    private suspend fun getLoadResultForRemote(
        query: String,
        page: Int
    ): PagingSource.LoadResult<Int, ImageResponseItem> {
        return when (val response = imageNetworkRepository.getImages(query, page)) {
            is ApiResult.Success ->
                if (page == Constants.DEFAULT_PAGE && response.data.hits?.isEmpty() == true) {
                    PagingSource.LoadResult.Error(Throwable(stringsResourceManager.getString(R.string.no_data_found)))
                } else {
                    val itemList = response.data.hits.orEmpty()
                    localDataProvider.onPageFetched(query, page, itemList)
                    PagingSource.LoadResult.Page(
                        data = itemList,
                        prevKey = getPreviousKey(page),
                        nextKey = getNextKey(
                            page,
                            itemList.count().default()
                        )
                    )
                }

            is ApiResult.Failure ->
                PagingSource.LoadResult.Error(Throwable(response.error.message))
        }
    }

    private suspend fun getLoadResultForDatabase(page: Int): PagingSource.LoadResult<Int, ImageResponseItem> {
        val from = page.dec().times(Constants.PAGE_LIMIT)
        val to = from.plus(Constants.PAGE_LIMIT)
        val totalItems = getListFromDatabase()
        val persistedList = totalItems.safeSubList(from, to)
        return PagingSource.LoadResult.Page(
            data = persistedList,
            prevKey = getPreviousKey(page),
            nextKey = getNextKey(
                page,
                persistedList.count().default()
            )
        )
    }

    private suspend fun getListFromDatabase(): List<ImageResponseItem> {
        return localDataProvider.getAllImages()
    }

    private fun getPreviousKey(page: Int): Int? =
        if (Constants.DEFAULT_PAGE == page) null else page

    private fun getNextKey(page: Int, currentFetchedItemsCount: Int): Int? =
        if (currentFetchedItemsCount < Constants.PAGE_LIMIT) null else page.inc()
}