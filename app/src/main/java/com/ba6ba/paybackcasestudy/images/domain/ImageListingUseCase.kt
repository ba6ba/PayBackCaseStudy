package com.ba6ba.paybackcasestudy.images.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.ba6ba.paybackcasestudy.common.FlowUseCase
import com.ba6ba.paybackcasestudy.common.default
import com.ba6ba.paybackcasestudy.images.data.ImageItemUiData
import com.ba6ba.paybackcasestudy.images.data.ImageRepository
import com.ba6ba.paybackcasestudy.images.data.ImageResponseItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ImageListingUseCase : FlowUseCase<String, PagingData<ImageItemUiData>> {
    override fun dispatcher(): CoroutineDispatcher = Dispatchers.IO
}

class DefaultImageListingUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) : ImageListingUseCase {
    override fun execute(parameters: String): Flow<PagingData<ImageItemUiData>> =
        imageRepository.getImages(parameters).map { pagingData ->
            pagingData.map { imageResponseItem ->
                mapRepoToImageUiData(imageResponseItem)
            }
        }

    private fun mapRepoToImageUiData(imageResponseItem: ImageResponseItem): ImageItemUiData {
        return ImageItemUiData(
            imageUrl = imageResponseItem.previewURL.default,
            userName = imageResponseItem.user.default,
            tags = imageResponseItem.tags?.split(", ").orEmpty().take(3),
            metadata = ImageItemUiData.Metadata(
                hdImageUrl = imageResponseItem.largeImageURL.default,
                tags = imageResponseItem.tags.default,
                likes = imageResponseItem.likes ?: 0L,
                comments = imageResponseItem.comments ?: 0L,
                downloads = imageResponseItem.downloads ?: 0L,
            )
        )
    }
}