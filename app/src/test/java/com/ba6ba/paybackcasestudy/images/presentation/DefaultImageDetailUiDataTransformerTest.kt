package com.ba6ba.paybackcasestudy.images.presentation

import com.ba6ba.paybackcasestudy.BaseTest
import com.ba6ba.paybackcasestudy.images.data.ImageDetailArgsData
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultImageDetailUiDataTransformerTest : BaseTest() {

    private lateinit var imageDetailUiDataTransformer: ImageDetailUiDataTransformer

    @Before
    fun setUp() {
        imageDetailUiDataTransformer = DefaultImageDetailUiDataTransformer()
    }

    @Test
    fun `verify transformation of args data to detail ui data`() {
        val imageDetailArgsData = ImageDetailArgsData(
            previewUrl = "preview",
            hdImageUrl = "hd image url",
            userName = "user name",
            "fruits, vegetables, food",
            12,
            12,
            12
        )

        val uiData = imageDetailUiDataTransformer.transform(imageDetailArgsData)

        assertEquals(3, uiData.tags.count())
        assertEquals(imageDetailArgsData.comments.toString(), uiData.comments)
    }
}