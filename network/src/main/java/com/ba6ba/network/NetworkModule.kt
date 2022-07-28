package com.ba6ba.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface NetworkModule {

    @Binds
    fun bindOKHttpClientProvider(default: DefaultOKHttpClientProvider): OKHttpClientProvider

    @Binds
    fun bindRetrofitProvider(default: DefaultRetrofitProvider): RetrofitProvider
}