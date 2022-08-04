package com.ba6ba.paybackcasestudy.images.data

import com.ba6ba.network.ApiResult
import com.ba6ba.paybackcasestudy.common.default
import com.ba6ba.paybackcasestudy.images.domain.Images
import javax.inject.Inject

interface ImageRepository {

    suspend fun getImages(fetchImageMode: FetchImageMode): ImageFetchResult
    suspend fun refresh()
}

class DefaultImageRepository @Inject constructor(
    private val imageLocalDataSource: ImageLocalDataSource,
    private val imageNetworkDataSource: ImageNetworkDataSource
) : ImageRepository {
    override suspend fun getImages(fetchImageMode: FetchImageMode): ImageFetchResult {
        return when (fetchImageMode) {
            FetchImageMode.Database -> {
                val storedImages = imageLocalDataSource.getAllImages()
                if (storedImages.isEmpty()) {
                    ImageFetchResult.Empty
                } else {
                    ImageFetchResult.Data(storedImages)
                }
            }

            is FetchImageMode.Remote -> {
                when (val apiResult =
                    imageNetworkDataSource.getImages(fetchImageMode.query, fetchImageMode.page)) {
                    is ApiResult.Success -> {
                        val fetchedList =
                            apiResult.data.hits.orEmpty().map { mapImageResponseItemToImages(it) }
                        if (fetchedList.isEmpty()) {
                            ImageFetchResult.Empty
                        } else {
                            imageLocalDataSource.insertAll(fetchedList)
                            ImageFetchResult.Data(fetchedList)
                        }
                    }

                    is ApiResult.Failure ->
                        ImageFetchResult.Error(apiResult.error.message)
                }
            }
        }
    }

    override suspend fun refresh() {
        imageLocalDataSource.refresh()
    }

    private fun mapImageResponseItemToImages(imageResponseItem: ImageResponseItem): Images {
        return Images(
            imageResponseItem.id.default(),
            imageResponseItem.tags.orEmpty(),
            imageResponseItem.previewURL.default,
            imageResponseItem.largeImageURL.default,
            imageResponseItem.downloads.default(),
            imageResponseItem.likes.default(),
            imageResponseItem.comments.default(),
            imageResponseItem.user.default(),
        )
    }
}