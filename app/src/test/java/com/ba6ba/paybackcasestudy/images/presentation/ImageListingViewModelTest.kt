package com.ba6ba.paybackcasestudy.images.presentation

import com.ba6ba.paybackcasestudy.common.LightDarkModeManager
import com.ba6ba.paybackcasestudy.common.StringsResourceManager
import com.ba6ba.paybackcasestudy.images.data.ImageDetailArgsData
import com.ba6ba.paybackcasestudy.images.data.ImageItemUiData
import com.ba6ba.paybackcasestudy.images.domain.ImageListingUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class ImageListingViewModelTest {

    @Mock
    lateinit var lightDarkModeManager: LightDarkModeManager

    @Mock
    lateinit var imageListingUseCase: ImageListingUseCase

    @Mock
    lateinit var stringsResourceManager: StringsResourceManager

    private lateinit var imageListingViewModel: ImageListingViewModel

    @Before
    fun setUp() {
        imageListingViewModel =
            ImageListingViewModel(lightDarkModeManager, imageListingUseCase, stringsResourceManager)
    }

    @Test
    fun getArgsData() {
        val imageItemUiData = ImageItemUiData(
            imageUrl = "preview url",
            userName = "user name",
            tags = emptyList(),
            metadata = ImageItemUiData.Metadata(
                hdImageUrl = "hd image url",
                comments = 499L,
                likes = 13L,
                downloads = 1L,
                tags = "fruits, veg, food"
            )
        )
        val imageDetailArgsData = ImageDetailArgsData(
            hdImageUrl = imageItemUiData.metadata.hdImageUrl,
            previewUrl = imageItemUiData.imageUrl,
            userName = imageItemUiData.userName,
            likes = imageItemUiData.metadata.likes,
            comments = imageItemUiData.metadata.comments,
            downloads = imageItemUiData.metadata.downloads,
            tags = imageItemUiData.metadata.tags
        )
        val actualArgsData = imageListingViewModel.getArgsData(imageItemUiData)

        assertEquals(imageDetailArgsData, actualArgsData)
    }
}