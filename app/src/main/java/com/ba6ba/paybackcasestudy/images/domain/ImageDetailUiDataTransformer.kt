package com.ba6ba.paybackcasestudy.images.domain

import com.ba6ba.paybackcasestudy.common.UiDataTransformer
import com.ba6ba.paybackcasestudy.images.data.ImageDetailArgsData
import com.ba6ba.paybackcasestudy.images.data.ImageItemDetailUiData
import javax.inject.Inject

interface ImageDetailUiDataTransformer :
    UiDataTransformer<ImageDetailArgsData, ImageItemDetailUiData>

class DefaultImageDetailUiDataTransformer @Inject constructor() : ImageDetailUiDataTransformer {
    override fun transform(from: ImageDetailArgsData): ImageItemDetailUiData {
        return ImageItemDetailUiData(
            hdImageUrl = from.hdImageUrl,
            likes = "${from.likes}",
            comments = "${from.comments}",
            downloads = "${from.downloads}",
            userName = from.userName,
            tags = from.tags.split(", ")
        )
    }
}