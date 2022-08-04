package com.ba6ba.paybackcasestudy.images.domain

import com.ba6ba.paybackcasestudy.BaseTest
import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.common.StringsResourceManager
import com.ba6ba.paybackcasestudy.metadata.data.MetadataRepository
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultFetchSavedQueryUseCaseTest : BaseTest() {

    @Mock
    lateinit var metadataRepository: MetadataRepository

    @Mock
    lateinit var stringsResourceManager: StringsResourceManager

    lateinit var fetchSavedQueryUseCase: FetchSavedQueryUseCase

    @Before
    fun setup() {
        fetchSavedQueryUseCase =
            DefaultFetchSavedQueryUseCase(metadataRepository, stringsResourceManager)
    }

    @Test
    fun `verify last saved query`() = runTest {
        val query = "fruits"
        whenever(metadataRepository.getLastSavedQuery()).thenReturn(query)

        assertEquals(query, fetchSavedQueryUseCase())
    }

    @Test
    fun `verify last saved query is null`() = runTest {
        val query = "oaks"
        whenever(metadataRepository.getLastSavedQuery()).thenReturn(null)
        whenever(stringsResourceManager.getString(eq(R.string.default_query))).thenReturn(query)

        assertEquals(query, fetchSavedQueryUseCase())
    }
}