package com.ba6ba.paybackcasestudy.images.data

import com.ba6ba.paybackcasestudy.database.ImagesDao
import com.ba6ba.paybackcasestudy.images.domain.Images
import javax.inject.Inject

interface ImageLocalDataSource {
    suspend fun refresh()
    suspend fun insertAll(list: List<Images>)
    suspend fun getAllImages(): List<Images>
}

class DefaultImageLocalDataSource @Inject constructor(
    private val imagesDao: ImagesDao
) : ImageLocalDataSource {

    override suspend fun refresh() {
        imagesDao.clearImages()
    }

    override suspend fun insertAll(list: List<Images>) {
        imagesDao.insertAll(list)
    }

    override suspend fun getAllImages(): List<Images> {
        return imagesDao.getAll()
    }
}