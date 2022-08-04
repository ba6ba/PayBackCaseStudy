package com.ba6ba.paybackcasestudy.images.data

import com.ba6ba.network.ApiResult
import com.ba6ba.network.ErrorResponse
import com.ba6ba.paybackcasestudy.BaseTest
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.exceptions.base.MockitoException
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultImageNetworkRepositoryTest : BaseTest() {

    @Mock
    lateinit var imageApiService: ImageApiService

    private lateinit var imageNetworkDataSource: ImageNetworkDataSource

    @Before
    fun setUp() {
        imageNetworkDataSource = DefaultImageNetworkDataSource(imageApiService)
    }

    @Test
    fun `verify api result error in case of invocation of service`() = runTest {
        val query = "fruits"
        val page = 3
        val expectedApiResult = ApiResult.technicalFailure()
        val errorMessage = expectedApiResult.error.message
        whenever(imageApiService.getImages(query, page)).thenThrow(MockitoException(errorMessage))

        val actualApiResult = imageNetworkDataSource.getImages(query, page)

        assertEquals(expectedApiResult, actualApiResult)
    }

    @Test
    fun `verify api result success in case of invocation of service`() = runTest {
        val query = "fruits"
        val page = 3
        val response = ImageListingResponse(hits = emptyList(), total = 0, totalHits = 400)
        val expectedApiResult = ApiResult.Success(response)
        whenever(imageApiService.getImages(query, page)).thenReturn(response)

        val actualApiResult = imageNetworkDataSource.getImages(query, page)

        assertEquals(expectedApiResult, actualApiResult)
    }
}