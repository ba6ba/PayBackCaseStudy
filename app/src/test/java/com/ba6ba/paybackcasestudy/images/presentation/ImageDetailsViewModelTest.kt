package com.ba6ba.paybackcasestudy.images.presentation

import androidx.lifecycle.SavedStateHandle
import com.ba6ba.paybackcasestudy.BaseTest
import com.ba6ba.paybackcasestudy.common.ArgsConstants
import com.ba6ba.paybackcasestudy.common.ArgsDataException
import com.ba6ba.paybackcasestudy.images.data.ImageDetailArgsData
import com.ba6ba.paybackcasestudy.images.data.ImageItemDetailUiData
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.hamcrest.MatcherAssert
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.exceptions.base.MockitoException
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)

class ImageDetailsViewModelTest : BaseTest() {

    @Mock
    lateinit var savedStateHandle: SavedStateHandle

    @Mock
    lateinit var imageDetailUiDataTransformer: ImageDetailUiDataTransformer

    private lateinit var imageDetailsViewModel: ImageDetailsViewModel

    @Before
    fun setUp() {
        imageDetailsViewModel =
            ImageDetailsViewModel(savedStateHandle, imageDetailUiDataTransformer)
    }

    @Test
    fun `verify image detail ui data is not null`() = runTest {
        val imageDetailArgsData =
            ImageDetailArgsData(
                "preview", "hd image",
                "user name", "fruits", 2, 2, 2
            )
        val expectedImageDetailUiData = ImageItemDetailUiData(
            hdImageUrl = "hd image",
            likes = "2",
            comments = "2",
            downloads = "2",
            userName = "user name",
            tags = listOf("fruits")
        )
        whenever(savedStateHandle.get<ImageDetailArgsData>(ArgsConstants.ARGS_DATA)).thenReturn(
            imageDetailArgsData
        )
        whenever(imageDetailUiDataTransformer.transform(imageDetailArgsData)).thenReturn(
            expectedImageDetailUiData
        )

        val actualImageDetailUiData = imageDetailsViewModel.imageDetailUiData.firstOrNull()

        assertNotNull(actualImageDetailUiData)
        assertEquals(expectedImageDetailUiData, actualImageDetailUiData)
    }


    @Test
    fun `verify image detail ui data is null`() = runTest {
        val imageDetailArgsData =
            ImageDetailArgsData(
                "preview", "hd image",
                "user name", "fruits", 2, 2, 2
            )
        val expectedImageDetailUiData = ImageItemDetailUiData(
            hdImageUrl = "hd image",
            likes = "2",
            comments = "2",
            downloads = "2",
            userName = "user name",
            tags = listOf("fruits")
        )
        whenever(savedStateHandle.get<ImageDetailArgsData>(ArgsConstants.ARGS_DATA)).thenReturn(
            imageDetailArgsData
        )
        whenever(imageDetailUiDataTransformer.transform(imageDetailArgsData)).thenReturn(null)

        val actualImageDetailUiData = imageDetailsViewModel.imageDetailUiData.firstOrNull()

        assertNull(actualImageDetailUiData)
    }

    @Test(expected = ArgsDataException::class)
    fun `verify saved state handle throws error`() = runTest {
        whenever(savedStateHandle.get<ImageDetailArgsData>(ArgsConstants.ARGS_DATA)).thenReturn(null)

        imageDetailsViewModel.imageDetailUiData.firstOrNull()
    }
}