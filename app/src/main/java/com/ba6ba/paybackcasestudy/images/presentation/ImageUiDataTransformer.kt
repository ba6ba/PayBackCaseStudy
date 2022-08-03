package com.ba6ba.paybackcasestudy.images.presentation

import com.ba6ba.paybackcasestudy.common.UiDataTransformer
import com.ba6ba.paybackcasestudy.common.default
import com.ba6ba.paybackcasestudy.images.data.ImageItemUiData
import com.ba6ba.paybackcasestudy.images.data.ImageResponseItem
import javax.inject.Inject

interface ImageUiDataTransformer :
    UiDataTransformer<ImageResponseItem, ImageItemUiData>

class DefaultImageUiDataTransformer @Inject constructor(): ImageUiDataTransformer {
    override fun transform(from: ImageResponseItem): ImageItemUiData {
        return ImageItemUiData(
            imageUrl = from.previewURL.default,
            userName = from.user.default,
            tags = from.tags?.split(", ").orEmpty().take(3),
            metadata = ImageItemUiData.Metadata(
                hdImageUrl = from.largeImageURL.default,
                tags = from.tags.default,
                likes = from.likes ?: 0L,
                comments = from.comments ?: 0L,
                downloads = from.downloads ?: 0L,
            )
        )
    }
}