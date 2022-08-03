package com.ba6ba.paybackcasestudy.images.data

import com.ba6ba.paybackcasestudy.database.ImagesDao
import javax.inject.Inject

interface ImageLocalDataSource {
    suspend fun refresh()
    suspend fun insertAll(list: List<ImageResponseItem>)
    suspend fun getAllImages(): List<ImageResponseItem>
}

class DefaultImageLocalStorageDataSource @Inject constructor(
    private val imagesDao: ImagesDao
) : ImageLocalDataSource {

    override suspend fun refresh() {
        imagesDao.clearImages()
    }

    override suspend fun insertAll(list: List<ImageResponseItem>) {
        imagesDao.insertAll(list)
    }

    override suspend fun getAllImages(): List<ImageResponseItem> {
        return imagesDao.getAll()
    }
}