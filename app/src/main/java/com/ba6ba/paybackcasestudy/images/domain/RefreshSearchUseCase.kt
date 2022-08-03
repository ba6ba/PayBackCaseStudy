package com.ba6ba.paybackcasestudy.images.domain

import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.common.StringsResourceManager
import com.ba6ba.paybackcasestudy.common.SuspendUseCase
import com.ba6ba.paybackcasestudy.images.data.ImageRepository
import com.ba6ba.paybackcasestudy.metadata.data.MetadataRepository
import javax.inject.Inject

interface RefreshSearchUseCase : SuspendUseCase<Unit>

class DefaultRefreshSearchUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
    private val metadataRepository: MetadataRepository
) : RefreshSearchUseCase {

    override suspend fun execute() {
        imageRepository.refresh()
        metadataRepository.refresh()
    }
}