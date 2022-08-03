package com.ba6ba.paybackcasestudy.metadata.di

import com.ba6ba.paybackcasestudy.metadata.data.DefaultMetadataLocalDataSource
import com.ba6ba.paybackcasestudy.metadata.data.DefaultMetadataRepository
import com.ba6ba.paybackcasestudy.metadata.data.MetadataLocalDataSource
import com.ba6ba.paybackcasestudy.metadata.data.MetadataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface MetadataModule {
    @Binds
    fun bindMetadataRepository(default: DefaultMetadataRepository): MetadataRepository

    @Binds
    fun bindMetadataLocalDataSource(default: DefaultMetadataLocalDataSource): MetadataLocalDataSource
}