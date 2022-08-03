package com.ba6ba.paybackcasestudy.metadata.data

import com.ba6ba.paybackcasestudy.common.Constants
import com.ba6ba.paybackcasestudy.common.default
import com.ba6ba.paybackcasestudy.database.Metadata
import com.ba6ba.paybackcasestudy.database.MetadataDao
import javax.inject.Inject

interface MetadataLocalDataSource {
    suspend fun getLastFetchedPage(query: String): Int
    suspend fun getLastSavedQuery(): String?
    suspend fun refresh()
    suspend fun insert(metadata: Metadata)
}

class DefaultMetadataLocalDataSource @Inject constructor(
    private val metadataDao: MetadataDao
) : MetadataLocalDataSource {
    override suspend fun getLastFetchedPage(query: String): Int {
        return metadataDao.get(query)?.lastFetchedPage.default(Constants.DEFAULT_PAGE)
    }

    override suspend fun getLastSavedQuery(): String? {
        return metadataDao.getAll()?.firstOrNull()?.query
    }

    override suspend fun refresh() {
        metadataDao.delete()
    }

    override suspend fun insert(metadata: Metadata) {
        metadataDao.insert(metadata)
    }
}