package com.ba6ba.paybackcasestudy.metadata.data

import com.ba6ba.paybackcasestudy.BaseTest
import com.ba6ba.paybackcasestudy.common.Constants
import com.ba6ba.paybackcasestudy.common.createList
import com.ba6ba.paybackcasestudy.database.Metadata
import com.ba6ba.paybackcasestudy.database.MetadataDao
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
class DefaultMetadataRepositoryTest: BaseTest() {

    @Mock
    lateinit var metadataDao: MetadataDao

    lateinit var metadataLocalDataSource: MetadataLocalDataSource

    @Before
    fun setUp() {
        metadataLocalDataSource = DefaultMetadataLocalDataSource(metadataDao)
    }

    @Test
    fun `verify get last fetched page`() = runTest {
        val query = "fruits"
        whenever(metadataDao.get(query)).thenReturn(null)

        val actualPage = metadataLocalDataSource.getLastFetchedPage(query)

        assertEquals(Constants.DEFAULT_PAGE, actualPage)
    }

    @Test
    fun `verify get last saved query`() = runTest {
        val metadata = Metadata(lastFetchedPage = 3, query = "fruits")
        whenever(metadataDao.getAll()).thenReturn(metadata.createList(1))

        val actualQuery = metadataLocalDataSource.getLastSavedQuery()

        assertEquals("fruits", actualQuery)
    }

    @Test
    fun `verify refresh`() = runTest {
        metadataLocalDataSource.refresh()

        verify(metadataDao).delete()
    }

    @Test
    fun `verify insert`() = runTest {
        val metadata = Metadata(lastFetchedPage = 3, query = "fruits")
        metadataLocalDataSource.insert(metadata)

        verify(metadataDao).insert(metadata)
    }
}