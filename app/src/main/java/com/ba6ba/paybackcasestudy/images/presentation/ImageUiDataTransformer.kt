package com.ba6ba.paybackcasestudy.images.presentation

import com.ba6ba.paybackcasestudy.common.UiDataTransformer
import com.ba6ba.paybackcasestudy.common.default
import com.ba6ba.paybackcasestudy.images.data.ImageItemUiData
import com.ba6ba.paybackcasestudy.images.domain.Images
import javax.inject.Inject

interface ImageUiDataTransformer :
    UiDataTransformer<Images, ImageItemUiData>

class DefaultImageUiDataTransformer @Inject constructor() : ImageUiDataTransformer {
    override fun transform(from: Images): ImageItemUiData {
        return ImageItemUiData(
            imageUrl = from.previewURL.default,
            userName = from.user.default,
            tags = from.tags.split(", ").take(3),
            metadata = ImageItemUiData.Metadata(
                hdImageUrl = from.largeImageURL.default,
                tags = from.tags.default,
                likes = from.likes,
                comments = from.comments,
                downloads = from.downloads,
            )
        )
    }
}