package com.ba6ba.paybackcasestudy.images.di

import com.ba6ba.network.RetrofitProvider
import com.ba6ba.paybackcasestudy.images.data.*
import com.ba6ba.paybackcasestudy.images.domain.*
import com.ba6ba.paybackcasestudy.images.presentation.DefaultImageDetailUiDataTransformer
import com.ba6ba.paybackcasestudy.images.presentation.ImageDetailUiDataTransformer
import com.ba6ba.paybackcasestudy.images.presentation.DefaultImageUiDataTransformer
import com.ba6ba.paybackcasestudy.images.presentation.ImageUiDataTransformer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.create

@InstallIn(ViewModelComponent::class)
@Module
interface ImagesModule {

    @Binds
    fun bindImageRepository(default: DefaultImageRepository): ImageRepository

    @Binds
    fun bindImageDetailUiDataTransformer(default: DefaultImageDetailUiDataTransformer): ImageDetailUiDataTransformer

    @Binds
    fun bindImageUiDataTransformer(default: DefaultImageUiDataTransformer): ImageUiDataTransformer

    @Binds
    fun bindImageNetworkRepository(default: DefaultImageNetworkRepository): ImageNetworkDataSource

    @Binds
    fun bindImageLocalDataSource(default: DefaultImageLocalStorageDataSource): ImageLocalDataSource
}

@InstallIn(ViewModelComponent::class)
@Module
interface UseCaseModule {
    @Binds
    fun bindImageListingUseCase(default: DefaultImageListingUseCase): ImageListingUseCase

    @Binds
    fun bindRefreshSearchSuspendUseCase(default: DefaultRefreshSearchUseCase): RefreshSearchUseCase

    @Binds
    fun bindFetchSavedQueryUseCase(default: DefaultFetchSavedQueryUseCase): FetchSavedQueryUseCase
}

@InstallIn(ViewModelComponent::class)
@Module
class ImageServiceModule {
    @Provides
    fun provideImageApiService(retrofitProvider: RetrofitProvider): ImageApiService =
        retrofitProvider.get().create()
}