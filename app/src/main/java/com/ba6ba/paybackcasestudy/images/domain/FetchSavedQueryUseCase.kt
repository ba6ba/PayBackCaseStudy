package com.ba6ba.paybackcasestudy.images.domain

import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.common.StringsResourceManager
import com.ba6ba.paybackcasestudy.common.SuspendUseCase
import com.ba6ba.paybackcasestudy.metadata.data.MetadataRepository
import javax.inject.Inject

interface FetchSavedQueryUseCase : SuspendUseCase<String>

class DefaultFetchSavedQueryUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository,
    private val stringsResourceManager: StringsResourceManager
) : FetchSavedQueryUseCase {

    override suspend fun execute(): String {
        return metadataRepository.getLastSavedQuery()
            ?: stringsResourceManager.getString(R.string.default_query)
    }
}