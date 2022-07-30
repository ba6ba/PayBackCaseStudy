package com.ba6ba.paybackcasestudy.images

import com.ba6ba.network.RetrofitProvider
import com.ba6ba.paybackcasestudy.images.data.*
import com.ba6ba.paybackcasestudy.images.domain.DefaultImageListingUseCase
import com.ba6ba.paybackcasestudy.images.domain.ImageListingUseCase
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
    fun bindImagePagingSourceProvider(default: DefaultImagePagingSourceProvider): ImagePagingSourceProvider

    @Binds
    fun bindImageRepository(default: DefaultImageRepository): ImageRepository

    @Binds
    fun bindImageListingUseCase(default: DefaultImageListingUseCase): ImageListingUseCase
}

@InstallIn(ViewModelComponent::class)
@Module
class ImageServiceModule {
    @Provides
    fun provideImageApiService(retrofitProvider: RetrofitProvider): ImageApiService =
        retrofitProvider.get().create()
}