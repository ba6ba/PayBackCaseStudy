package com.ba6ba.paybackcasestudy.common

import javax.inject.Inject

interface LocalDataSource {
    fun getCurrentDisplayMode(): Int
    fun setCurrentDisplayMode(mode: Int)
}

class DefaultLocalDataSource @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) : LocalDataSource {
    override fun getCurrentDisplayMode(): Int {
        return sharedPreferencesManager.getCurrentDisplayMode()
    }

    override fun setCurrentDisplayMode(mode: Int) {
        sharedPreferencesManager.setCurrentDisplayMode(mode)
    }
}