package com.ba6ba.paybackcasestudy.images.domain

import com.ba6ba.paybackcasestudy.images.data.ImagePagingSourceProvider
import org.junit.Assert.*

import org.junit.Before
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
        imageListingUseCase = DefaultImageListingSuspendUseCase(imagePagingSourceProvider)
    }
}