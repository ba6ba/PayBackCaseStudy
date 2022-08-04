package com.ba6ba.paybackcasestudy.images.presentation

import com.ba6ba.paybackcasestudy.BaseTest
import com.ba6ba.paybackcasestudy.images.domain.Images
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultImageUiDataTransformerTest : BaseTest() {

    lateinit var imageUiDataTransformer: ImageUiDataTransformer

    @Before
    fun setUp() {
        imageUiDataTransformer = DefaultImageUiDataTransformer()
    }

    @Test
    fun `verify transformation of images to image ui data`() {
        val images =
            Images(
                32, "fruits, vegetables", "preview url",
                "large image url", 1, 1, 1,
                "user"
            )
        val imageUiData = imageUiDataTransformer.transform(images)

        assertEquals(2, imageUiData.tags.count())
        assertEquals(images.largeImageURL, imageUiData.metadata.hdImageUrl)
    }
}