package com.ba6ba.paybackcasestudy.images.domain

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ba6ba.paybackcasestudy.common.Constants
import com.ba6ba.paybackcasestudy.common.StringsResourceManager
import com.ba6ba.paybackcasestudy.images.data.ImageRepository
import com.ba6ba.paybackcasestudy.images.data.ImageResponseItem
import com.ba6ba.paybackcasestudy.metadata.data.MetadataRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultImageListingUseCaseTest {

    @Mock
    lateinit var imageRepository: ImageRepository

    @Mock
    lateinit var metadataRepository: MetadataRepository

    @Mock
    lateinit var stringsResourceManager: StringsResourceManager

    lateinit var imageListingUseCase: ImageListingUseCase

    @Before
    fun setUp() {
        imageListingUseCase =
            DefaultImageListingUseCase(imageRepository, metadataRepository, stringsResourceManager)
    }

    @Test
    fun `verify paging source gets error when fetching from remote`() = runBlocking {
        val expectedPagingSource = PagingSource.LoadResult.Error<Int, ImageResponseItem>(Throwable(""))
        val loadParams = PagingSource.LoadParams.Refresh(1, Constants.DEFAULT_PAGE, false)

        val actualPagingSource = imageListingUseCase("fruits").load(loadParams)

        assertEquals(expectedPagingSource, actualPagingSource)
    }
}