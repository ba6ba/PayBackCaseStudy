package com.ba6ba.paybackcasestudy.images.domain

import com.ba6ba.paybackcasestudy.BaseTest
import com.ba6ba.paybackcasestudy.images.data.ImageRepository
import com.ba6ba.paybackcasestudy.metadata.data.MetadataRepository
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultRefreshSearchUseCaseTest : BaseTest() {

    @Mock
    lateinit var imageRepository: ImageRepository

    @Mock
    lateinit var metadataRepository: MetadataRepository

    private lateinit var refreshSearchUseCase: RefreshSearchUseCase

    @Before
    fun setUp() {
        refreshSearchUseCase = DefaultRefreshSearchUseCase(imageRepository, metadataRepository)
    }

    @Test
    fun `verify interactions with mock on invocation`() = runTest {
        refreshSearchUseCase()

        verify(metadataRepository).refresh()
        verify(imageRepository).refresh()
    }
}