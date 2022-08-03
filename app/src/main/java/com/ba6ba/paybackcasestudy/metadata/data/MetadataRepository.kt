package com.ba6ba.paybackcasestudy.metadata.data

import com.ba6ba.paybackcasestudy.database.Metadata
import javax.inject.Inject

interface MetadataRepository {
    suspend fun getLastFetchedPage(query: String): Int
    suspend fun getLastSavedQuery(): String?
    suspend fun refresh()
    suspend fun insert(query: String, page: Int)
}

class DefaultMetadataRepository @Inject constructor(
    private val metadataLocalDataSource: MetadataLocalDataSource
) : MetadataRepository {
    override suspend fun getLastFetchedPage(query: String): Int {
        return metadataLocalDataSource.getLastFetchedPage(query)
    }

    override suspend fun getLastSavedQuery(): String? {
        return metadataLocalDataSource.getLastSavedQuery()
    }

    override suspend fun refresh() {
        metadataLocalDataSource.refresh()
    }

    override suspend fun insert(query: String, page: Int) {
        metadataLocalDataSource.insert(Metadata(query, page))
    }
}