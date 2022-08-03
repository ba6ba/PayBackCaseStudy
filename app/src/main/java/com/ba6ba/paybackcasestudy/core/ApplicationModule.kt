package com.ba6ba.paybackcasestudy.core

import com.ba6ba.paybackcasestudy.common.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ApplicationModule {

    @Binds
    fun bindLocalDataSource(default: DefaultLocalDataSource): LocalDataSource

    @Binds
    fun bindSharedPreferencesManager(default: DefaultSharedPreferencesManager): SharedPreferencesManager

    @Binds
    fun bindStringsResourceManager(default: DefaultStringsResourceManager): StringsResourceManager

    @Binds
    fun bindLightDarkModeManager(default: DefaultLightDarkModeManager): LightDarkModeManager
}