package com.ba6ba.paybackcasestudy.common

import androidx.appcompat.app.AppCompatDelegate
import javax.inject.Inject

interface LightDarkModeManager {
    fun toggle()
    fun isDarkModeEnabled(): Boolean
    fun setCurrentMode()
}

class DefaultLightDarkModeManager @Inject constructor(
    private val localDataSource: LocalDataSource
) : LightDarkModeManager {
    override fun toggle() {
        if (isDarkModeEnabled()) {
            enableLightMode()
        } else {
            enableDarkMode()
        }
    }

    override fun isDarkModeEnabled(): Boolean {
        return localDataSource.getCurrentDisplayMode() == DayNightModeConstants.NIGHT_MODE
    }

    override fun setCurrentMode() {
        if (isDarkModeEnabled()) {
            enableDarkMode()
        } else {
            enableLightMode()
        }
    }

    private fun enableDarkMode() {
        localDataSource.setCurrentDisplayMode(DayNightModeConstants.NIGHT_MODE)
        setMode()
    }

    private fun enableLightMode() {
        localDataSource.setCurrentDisplayMode(DayNightModeConstants.DAY_MODE)
        setMode()
    }

    private fun setMode() {
        AppCompatDelegate.setDefaultNightMode(localDataSource.getCurrentDisplayMode())
    }
}