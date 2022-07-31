package com.ba6ba.paybackcasestudy.images.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ba6ba.paybackcasestudy.images.data.ImagePagingSourceProvider
import com.ba6ba.paybackcasestudy.images.data.ImageResponseItem
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultImageListingUseCaseTest {

    @Mock
    lateinit var imagePagingSourceProvider: ImagePagingSourceProvider

    lateinit var imageListingUseCase: ImageListingUseCase

    @Before
    fun setUp() {
        imageListingUseCase = DefaultImageListingUseCase(imagePagingSourceProvider)
    }
}