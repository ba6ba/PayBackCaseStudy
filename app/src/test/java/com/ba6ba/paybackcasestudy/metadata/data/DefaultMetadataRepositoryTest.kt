package com.ba6ba.paybackcasestudy.metadata.data

import com.ba6ba.paybackcasestudy.BaseTest
import com.ba6ba.paybackcasestudy.database.Metadata
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultMetadataRepositoryTest : BaseTest() {

    @Mock
    lateinit var metadataLocalDataSource: MetadataLocalDataSource

    lateinit var metadataRepository: MetadataRepository

    @Before
    fun setUp() {
        metadataRepository = DefaultMetadataRepository(metadataLocalDataSource)
    }

    @Test
    fun `get last fetched page`() = runTest {
        val query = "oaks"
        metadataRepository.getLastFetchedPage(query)

        metadataLocalDataSource.getLastFetchedPage(query)
    }

    @Test
    fun `verify get last saved query`() = runTest {
        metadataRepository.getLastSavedQuery()

        verify(metadataLocalDataSource).getLastSavedQuery()
    }

    @Test
    fun `verify refresh`() = runTest {
        metadataRepository.refresh()

        verify(metadataLocalDataSource).refresh()
    }

    @Test
    fun `verify insert`() = runTest {
        val query = "fruits"
        val page = 3
        metadataRepository.insert(query, page)

        verify(metadataLocalDataSource).insert(Metadata(query, page))
    }

}