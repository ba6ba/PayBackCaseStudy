package com.ba6ba.paybackcasestudy.common

import com.ba6ba.paybackcasestudy.images.data.DefaultLocalDataProvider
import com.ba6ba.paybackcasestudy.images.data.LocalDataProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface ManagersModule {
    @Binds
    fun bindSharedPreferencesManager(default: DefaultSharedPreferencesManager): SharedPreferencesManager

    @Binds
    fun bindStringsResourceManager(default: DefaultStringsResourceManager): StringsResourceManager

    @Binds
    fun bindLightDarkModeManager(default: DefaultLightDarkModeManager): LightDarkModeManager

    @Binds
    fun bindLocalDataProvider(default: DefaultLocalDataProvider): LocalDataProvider

}