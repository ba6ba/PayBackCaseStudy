package com.ba6ba.paybackcasestudy.common

import android.content.Context
import androidx.core.content.edit
import com.ba6ba.paybackcasestudy.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface SharedPreferencesManager {
    fun getCurrentDisplayMode(): Int
    fun setCurrentDisplayMode(mode: Int)
}

class DefaultSharedPreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val stringsResourceManager: StringsResourceManager,
) : SharedPreferencesManager {

    private val sharedPreferences by lazy {
        context.getSharedPreferences(
            stringsResourceManager.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
    }

    override fun getCurrentDisplayMode(): Int {
        return sharedPreferences.getInt(KEY_CURRENT_DISPLAY_MODE, 0).default()
    }

    override fun setCurrentDisplayMode(mode: Int) {
        sharedPreferences.edit {
            putInt(KEY_CURRENT_DISPLAY_MODE, mode)
        }
    }

    companion object {
        private const val KEY_CURRENT_DISPLAY_MODE = "KEY_CURRENT_DISPLAY_MODE"
    }
}
