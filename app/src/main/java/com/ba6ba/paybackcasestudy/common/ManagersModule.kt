package com.ba6ba.paybackcasestudy.common

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

}