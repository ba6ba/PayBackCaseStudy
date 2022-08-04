package com.ba6ba.paybackcasestudy.images.domain

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.common.Constants
import com.ba6ba.paybackcasestudy.common.EMPTY_STRING
import com.ba6ba.paybackcasestudy.common.StringsResourceManager
import com.ba6ba.paybackcasestudy.common.createList
import com.ba6ba.paybackcasestudy.images.data.FetchImageMode
import com.ba6ba.paybackcasestudy.images.data.ImageFetchResult
import com.ba6ba.paybackcasestudy.images.data.ImageRepository
import com.ba6ba.paybackcasestudy.metadata.data.MetadataRepository
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
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
        val query = "fruits"
        val errorMessage = "not able to connect with internet"
        val fetchImageMode = FetchImageMode.Remote(1, query)
        val expectedPagingSource =
            PagingSource.LoadResult.Error<Int, Images>(Throwable(errorMessage))
        val loadParams = PagingSource.LoadParams.Refresh(1, Constants.DEFAULT_PAGE, false)
        whenever(metadataRepository.getLastFetchedPage(query)).thenReturn(1)
        whenever(imageRepository.getImages(fetchImageMode)).thenReturn(
            ImageFetchResult.Error(
                errorMessage
            )
        )

        val actualPagingSource = imageListingUseCase(query).load(loadParams)

        assertTrue(actualPagingSource is PagingSource.LoadResult.Error<Int, Images>)
        actualPagingSource as PagingSource.LoadResult.Error<Int, Images>
        assertEquals(expectedPagingSource.throwable.message, actualPagingSource.throwable.message)
    }

    @Test
    fun `verify paging source refresh key is null`() = runBlocking {
        val query = "fruits"
        val prevKey = null
        val nextKey = 2
        val data = Images(
            1L,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            0L,
            0L,
            0L,
            EMPTY_STRING
        ).createList(Constants.PAGE_LIMIT)
        val loadParams = PagingState(
            PagingSource.LoadResult.Page<Int, Images>(
                data,
                prevKey = prevKey,
                nextKey = nextKey
            ).createList(10), anchorPosition = null,
            config = PagingConfig(pageSize = Constants.PAGE_LIMIT),
            leadingPlaceholderCount = 10
        )

        assertNull(imageListingUseCase(query).getRefreshKey(loadParams))
    }

    @Test
    fun `verify paging source gets empty when fetching from remote`() = runBlocking {
        val query = "fruits"
        val errorMessage = "ummm, no data found"
        val fetchImageMode = FetchImageMode.Remote(1, query)
        val expectedPagingSource =
            PagingSource.LoadResult.Error<Int, Images>(Throwable(errorMessage))
        val loadParams = PagingSource.LoadParams.Refresh(1, Constants.DEFAULT_PAGE, false)
        whenever(metadataRepository.getLastFetchedPage(query)).thenReturn(1)
        whenever(imageRepository.getImages(fetchImageMode)).thenReturn(ImageFetchResult.Empty)
        whenever(stringsResourceManager.getString(eq(R.string.no_data_found))).thenReturn(
            errorMessage
        )

        val actualPagingSource = imageListingUseCase(query).load(loadParams)

        assertTrue(actualPagingSource is PagingSource.LoadResult.Error<Int, Images>)
        actualPagingSource as PagingSource.LoadResult.Error<Int, Images>
        assertEquals(expectedPagingSource.throwable.message, actualPagingSource.throwable.message)
    }

    @Test
    fun `verify paging source gets success when fetching from remote`() = runBlocking {
        val query = "fruits"
        val page = 1
        val prevKey = null
        val nextKey = 2
        val data = Images(
            1L,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            0L,
            0L,
            0L,
            EMPTY_STRING
        ).createList(Constants.PAGE_LIMIT)
        val fetchImageMode = FetchImageMode.Remote(page, query)
        val expectedPagingSource =
            PagingSource.LoadResult.Page<Int, Images>(data, prevKey = prevKey, nextKey = nextKey)
        val loadParams = PagingSource.LoadParams.Refresh(page, Constants.DEFAULT_PAGE, false)
        whenever(metadataRepository.getLastFetchedPage(query)).thenReturn(1)
        whenever(imageRepository.getImages(fetchImageMode)).thenReturn(ImageFetchResult.Data(data))

        val actualPagingSource = imageListingUseCase(query).load(loadParams)

        assertTrue(actualPagingSource is PagingSource.LoadResult.Page<Int, Images>)
        actualPagingSource as PagingSource.LoadResult.Page<Int, Images>
        assertEquals(expectedPagingSource.data, actualPagingSource.data)
        assertEquals(expectedPagingSource.nextKey, actualPagingSource.nextKey)
        assertEquals(expectedPagingSource.prevKey, actualPagingSource.prevKey)
        verify(metadataRepository).insert(query, page)
    }

    @Test
    fun `verify paging source gets success when fetching from database`() = runBlocking {
        val query = "fruits"
        val page = 1
        val prevKey = null
        val nextKey = 2
        val data = Images(
            1L,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            0L,
            0L,
            0L,
            EMPTY_STRING
        ).createList(Constants.PAGE_LIMIT.times(2))
        val fetchImageMode = FetchImageMode.Database
        val expectedPagingSource =
            PagingSource.LoadResult.Page<Int, Images>(data, prevKey = prevKey, nextKey = nextKey)
        val loadParams = PagingSource.LoadParams.Refresh(page, Constants.DEFAULT_PAGE, false)
        whenever(metadataRepository.getLastFetchedPage(query)).thenReturn(3)
        whenever(imageRepository.getImages(fetchImageMode)).thenReturn(ImageFetchResult.Data(data))

        val actualPagingSource = imageListingUseCase(query).load(loadParams)

        assertTrue(actualPagingSource is PagingSource.LoadResult.Page<Int, Images>)
        actualPagingSource as PagingSource.LoadResult.Page<Int, Images>
        assertEquals(expectedPagingSource.nextKey, actualPagingSource.nextKey)
        assertEquals(expectedPagingSource.prevKey, actualPagingSource.prevKey)
        assertEquals(Constants.PAGE_LIMIT, actualPagingSource.data.count())
    }

    @Test
    fun `verify paging source gets success with last page when fetching from database`() =
        runBlocking {
            val query = "fruits"
            val page = 1
            val prevKey = null
            val nextKey = 2
            val data = Images(
                1L,
                EMPTY_STRING,
                EMPTY_STRING,
                EMPTY_STRING,
                0L,
                0L,
                0L,
                EMPTY_STRING
            ).createList(Constants.PAGE_LIMIT.times(2))
            val fetchImageMode = FetchImageMode.Database
            val expectedPagingSource =
                PagingSource.LoadResult.Page<Int, Images>(
                    data,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            val loadParams = PagingSource.LoadParams.Refresh(page, Constants.DEFAULT_PAGE, false)
            whenever(metadataRepository.getLastFetchedPage(query)).thenReturn(page)
            whenever(imageRepository.getImages(fetchImageMode)).thenReturn(
                ImageFetchResult.Data(
                    data
                )
            )
            whenever(imageRepository.getImages(FetchImageMode.Database)).thenReturn(
                ImageFetchResult.Data(
                    data
                )
            )

            val actualPagingSource = imageListingUseCase(query).load(loadParams)

            assertTrue(actualPagingSource is PagingSource.LoadResult.Page<Int, Images>)
            actualPagingSource as PagingSource.LoadResult.Page<Int, Images>
            assertEquals(expectedPagingSource.nextKey, actualPagingSource.nextKey)
            assertEquals(expectedPagingSource.prevKey, actualPagingSource.prevKey)
            assertEquals(Constants.PAGE_LIMIT, actualPagingSource.data.count())
        }
}