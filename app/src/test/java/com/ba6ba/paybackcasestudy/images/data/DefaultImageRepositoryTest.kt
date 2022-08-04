package com.ba6ba.paybackcasestudy.images.data

import com.ba6ba.network.ApiResult
import com.ba6ba.network.ErrorResponse
import com.ba6ba.paybackcasestudy.BaseTest
import com.ba6ba.paybackcasestudy.common.EMPTY_STRING
import com.ba6ba.paybackcasestudy.common.createList
import com.ba6ba.paybackcasestudy.images.domain.Images
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultImageRepositoryTest : BaseTest() {

    @Mock
    lateinit var imageLocalDataSource: ImageLocalDataSource

    @Mock
    lateinit var imageNetworkDataSource: ImageNetworkDataSource

    private lateinit var imageRepository: ImageRepository

    @Before
    fun setUp() {
        imageRepository = DefaultImageRepository(imageLocalDataSource, imageNetworkDataSource)
    }

    @Test
    fun `verify refresh`() = runTest {
        imageRepository.refresh()

        verify(imageLocalDataSource).refresh()
    }

    @Test
    fun `verify get images are empty when fetch mode is remote`() = runTest {
        val query = "fruits"
        val page = 3
        val fetchImageMode = FetchImageMode.Remote(page, query)
        val expectedResult = ImageFetchResult.Empty
        val response = ImageListingResponse(emptyList(), 0, 400)
        whenever(imageNetworkDataSource.getImages(query, page)).thenReturn(
            ApiResult.Success(
                response
            )
        )

        val actualResult = imageRepository.getImages(fetchImageMode)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `verify get images return error when fetch mode is remote`() = runTest {
        val query = "fruits"
        val page = 3
        val fetchImageMode = FetchImageMode.Remote(page, query)
        val message = "no data found"
        val expectedResult = ImageFetchResult.Error(message)
        whenever(imageNetworkDataSource.getImages(query, page)).thenReturn(
            ApiResult.Failure(
                ErrorResponse(message = message, statusCode = 400)
            )
        )

        val actualResult = imageRepository.getImages(fetchImageMode)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `verify get images return list when fetch mode is remote`() = runTest {
        val query = "fruits"
        val page = 3
        val size = 4
        val fetchImageMode = FetchImageMode.Remote(page, query)
        val data = Images(
            12, "fruits, vegetables",
            "preview url",
            "large image url",
            23, 11,
            3, "user"
        ).createList(size)
        val responseImage = ImageResponseItem(
            12,
            EMPTY_STRING,
            EMPTY_STRING,
            "fruits, vegetables",
            "preview url",
            1,
            1,
            EMPTY_STRING,
            3,
            4,
            "large image url",
            EMPTY_STRING,
            EMPTY_STRING,
            3,
            5,
            59L,
            22L,
            23L,
            11L,
            3L,
            23L,
            "user",
            EMPTY_STRING,
        ).createList(size)
        val expectedResult = ImageFetchResult.Data(data)
        whenever(imageNetworkDataSource.getImages(query, page)).thenReturn(
            ApiResult.Success(
                ImageListingResponse(
                    hits = responseImage,
                    total = size,
                    totalHits = 203
                )
            )
        )

        val actualResult = imageRepository.getImages(fetchImageMode)

        assertEquals(expectedResult, actualResult)
        verify(imageLocalDataSource).insertAll(data)
    }

    @Test
    fun `verify get images return list when fetch mode is database`() = runTest {
        val size = 4
        val fetchImageMode = FetchImageMode.Database
        val data = Images(
            12, "fruits, vegetables",
            "preview url",
            "large image url",
            23, 11,
            3, "user"
        ).createList(size)
        val expectedResult = ImageFetchResult.Data(data)
        whenever(imageLocalDataSource.getAllImages()).thenReturn(data)

        val actualResult = imageRepository.getImages(fetchImageMode)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `verify get images are empty when fetch mode is database`() = runTest {
        val fetchImageMode = FetchImageMode.Database
        val expectedResult = ImageFetchResult.Empty
        val response = ImageListingResponse(emptyList(), 0, 400)
        whenever(imageLocalDataSource.getAllImages()).thenReturn(emptyList())

        val actualResult = imageRepository.getImages(fetchImageMode)

        assertEquals(expectedResult, actualResult)
    }

}