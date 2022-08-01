package com.ba6ba.paybackcasestudy.images.data

import androidx.room.withTransaction
import com.ba6ba.paybackcasestudy.common.Constants
import com.ba6ba.paybackcasestudy.common.default
import com.ba6ba.paybackcasestudy.database.ImagesMetadata
import com.ba6ba.paybackcasestudy.database.PayBackCaseStudyDatabase
import javax.inject.Inject

interface LocalDataProvider {
    suspend fun getLastFetchedPage(query: String): Int
    suspend fun onNewSearch()
    suspend fun getLastSavedQuery(): String?
    suspend fun onPageFetched(query: String, page: Int, list: List<ImageResponseItem>)
    suspend fun getAllImages(): List<ImageResponseItem>
}

class DefaultLocalDataProvider @Inject constructor(
    private val payBackCaseStudyDatabase: PayBackCaseStudyDatabase
) : LocalDataProvider {

    override suspend fun getLastFetchedPage(query: String): Int {
        return payBackCaseStudyDatabase.getImagesMetadataDao().get(query)
            ?.lastFetchedPage.default(Constants.DEFAULT_PAGE)
    }

    override suspend fun onNewSearch() {
        payBackCaseStudyDatabase.withTransaction {
            payBackCaseStudyDatabase.getImagesDao().clearImages()
            payBackCaseStudyDatabase.getImagesMetadataDao().delete()
        }
    }

    override suspend fun getLastSavedQuery(): String? {
        return payBackCaseStudyDatabase.getImagesMetadataDao().getAll()?.firstOrNull()?.query
    }

    override suspend fun onPageFetched(query: String, page: Int, list: List<ImageResponseItem>) {
        payBackCaseStudyDatabase.withTransaction {
            payBackCaseStudyDatabase.getImagesDao().insertAll(list)
            payBackCaseStudyDatabase.getImagesMetadataDao().insert(ImagesMetadata(query, page))
        }
    }

    override suspend fun getAllImages(): List<ImageResponseItem> {
        return payBackCaseStudyDatabase.getImagesDao().getAll()
    }
}