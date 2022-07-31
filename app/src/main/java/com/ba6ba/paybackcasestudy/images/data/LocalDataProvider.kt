package com.ba6ba.paybackcasestudy.images.data

import com.ba6ba.paybackcasestudy.common.Constants
import com.ba6ba.paybackcasestudy.common.SharedPreferencesManager
import javax.inject.Inject

interface LocalDataProvider {
    fun setLastFetchedPage(page: Int)
    fun getLastFetchedPage(): Int
    fun clearLastFetchedPage()
}

class DefaultLocalDataProvider @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) : LocalDataProvider {
    override fun setLastFetchedPage(page: Int) {
        sharedPreferencesManager.setLastFetchedPage(page)
    }

    override fun getLastFetchedPage(): Int {
        return sharedPreferencesManager.getLastFetchedPage()
    }

    override fun clearLastFetchedPage() {
        setLastFetchedPage(Constants.DEFAULT_PAGE)
    }
}