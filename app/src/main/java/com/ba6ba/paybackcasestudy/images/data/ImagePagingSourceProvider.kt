package com.ba6ba.paybackcasestudy.images.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ba6ba.network.ApiResult
import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.common.Constants
import com.ba6ba.paybackcasestudy.common.SharedPreferencesManager
import com.ba6ba.paybackcasestudy.common.StringsResourceManager
import com.ba6ba.paybackcasestudy.common.default
import com.ba6ba.paybackcasestudy.database.PayBackCaseStudyDatabase
import javax.inject.Inject

interface ImagePagingSourceProvider {
    fun get(query: String): PagingSource<Int, ImageResponseItem>
}

class DefaultImagePagingSourceProvider @Inject constructor(
    private val imageNetworkRepository: ImageNetworkRepository,
    private val payBackCaseStudyDatabase: PayBackCaseStudyDatabase,
    private val stringsResourceManager: StringsResourceManager,
    private val localDataProvider: LocalDataProvider
) : ImagePagingSourceProvider {

    override fun get(query: String): PagingSource<Int, ImageResponseItem> {
        return object : PagingSource<Int, ImageResponseItem>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageResponseItem> {
                val loadResult: LoadResult<Int, ImageResponseItem>
                val page = params.key ?: Constants.DEFAULT_PAGE
                val pageFetchState: PageFetchState =
                    if (page < localDataProvider.getLastFetchedPage()) {
                        PageFetchState.Database
                    } else if (page == localDataProvider.getLastFetchedPage()
                        && getListFromDatabase().isNotEmpty()
                    ) {
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
                    payBackCaseStudyDatabase.getImagesDao().insertAll(itemList)
                    localDataProvider.setLastFetchedPage(page)
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
        val to = from.plus(Constants.PAGE_LIMIT).dec()
        val persistedList = getListFromDatabase().subList(from, to)
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
        return payBackCaseStudyDatabase.getImagesDao().getAll()
    }

    private fun getPreviousKey(page: Int): Int? =
        if (Constants.DEFAULT_PAGE == page) null else page

    private fun getNextKey(page: Int, currentFetchedItemsCount: Int): Int? =
        if (currentFetchedItemsCount < Constants.PAGE_LIMIT) null else page.inc()
}