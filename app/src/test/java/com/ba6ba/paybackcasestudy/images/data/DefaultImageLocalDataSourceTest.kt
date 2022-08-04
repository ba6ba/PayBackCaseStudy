package com.ba6ba.paybackcasestudy.images.data

import com.ba6ba.paybackcasestudy.BaseTest
import com.ba6ba.paybackcasestudy.common.createList
import com.ba6ba.paybackcasestudy.database.ImagesDao
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
import kotlin.math.exp

@RunWith(MockitoJUnitRunner::class)
class DefaultImageLocalDataSourceTest : BaseTest() {

    @Mock
    lateinit var imagesDao: ImagesDao

    private lateinit var imageLocalDataSource: ImageLocalDataSource

    @Before
    fun setUp() {
        imageLocalDataSource = DefaultImageLocalDataSource(imagesDao)
    }

    @Test
    fun `verify refresh`() = runTest {
        imageLocalDataSource.refresh()

        verify(imagesDao).clearImages()
    }

    @Test
    fun `verify insertAll`() = runTest {
        val list =
            Images(
                12, "fruits, vegetables",
                "preview url",
                "large image url",
                23, 11,
                3, "user"
            ).createList(4)
        imageLocalDataSource.insertAll(list)

        verify(imagesDao).insertAll(list)
    }

    @Test
    fun `verify getAllImages`() = runTest {
        val expectedList =
            Images(
                12, "fruits, vegetables",
                "preview url",
                "large image url",
                23, 11,
                3, "user"
            ).createList(4)
        whenever(imagesDao.getAll()).thenReturn(expectedList)

        val actualList = imageLocalDataSource.getAllImages()

        assertEquals(expectedList, actualList)
    }
}