package com.ba6ba.paybackcasestudy.images.domain

import androidx.paging.*
import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.common.Constants
import com.ba6ba.paybackcasestudy.common.UseCase
import com.ba6ba.paybackcasestudy.common.StringsResourceManager
import com.ba6ba.paybackcasestudy.images.data.ImageRepository
import com.ba6ba.paybackcasestudy.images.data.ImageResponseItem
import com.ba6ba.paybackcasestudy.images.data.FetchImageMode
import com.ba6ba.paybackcasestudy.images.data.ImageFetchResult
import com.ba6ba.paybackcasestudy.metadata.data.MetadataRepository
import javax.inject.Inject

interface ImageListingUseCase : UseCase<String, PagingSource<Int, ImageResponseItem>>

class DefaultImageListingUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
    private val metadataRepository: MetadataRepository,
    private val stringsResourceManager: StringsResourceManager
) : ImageListingUseCase {

    override fun execute(parameters: String): PagingSource<Int, ImageResponseItem> =
        object : PagingSource<Int, ImageResponseItem>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageResponseItem> {
                val loadResult: LoadResult<Int, ImageResponseItem>
                val page = params.key ?: Constants.DEFAULT_PAGE
                val lastFetchedPage =
                    metadataRepository.getLastFetchedPage(query = parameters)
                val fetchImageMode: FetchImageMode =
                    if (page < lastFetchedPage) {
                        FetchImageMode.Database
                    } else if (page == lastFetchedPage && hasImagesInDatabase()) {
                        FetchImageMode.Database
                    } else {
                        FetchImageMode.Remote(page, parameters)
                    }

                loadResult = when (val result = imageRepository.getImages(fetchImageMode)) {
                    is ImageFetchResult.Error ->
                        LoadResult.Error(Throwable(result.message))

                    is ImageFetchResult.Empty ->
                        LoadResult.Error(
                            Throwable(
                                stringsResourceManager.getString(
                                    R.string.no_data_found
                                )
                            )
                        )

                    is ImageFetchResult.Data -> {
                        if (fetchImageMode is FetchImageMode.Remote) {
                            metadataRepository.insert(parameters, page)
                        }
                        LoadResult.Page(
                            data = result.data,
                            prevKey = getPreviousKey(page),
                            nextKey = getNextKey(page, result.data.count())
                        )
                    }
                }
                return loadResult
            }

            override fun getRefreshKey(
                state: PagingState<Int, ImageResponseItem>
            ): Int? = null
        }

    private suspend fun hasImagesInDatabase(): Boolean {
        val result = imageRepository.getImages(FetchImageMode.Database)
        return result is ImageFetchResult.Data && result.data.isNotEmpty()
    }

    private fun getPreviousKey(page: Int): Int? =
        if (Constants.DEFAULT_PAGE == page) null else page

    private fun getNextKey(page: Int, currentFetchedItemsCount: Int): Int? =
        if (currentFetchedItemsCount < Constants.PAGE_LIMIT) null else page.inc()
}