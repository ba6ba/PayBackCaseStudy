package com.ba6ba.paybackcasestudy.common

import androidx.appcompat.app.AppCompatDelegate
import com.ba6ba.network.Constants

object DayNightModeConstants {
    const val NIGHT_MODE = AppCompatDelegate.MODE_NIGHT_YES
    const val DAY_MODE = AppCompatDelegate.MODE_NIGHT_NO
}

object Constants {
    const val PAGE_LIMIT: Int = 20
    const val DEFAULT_PAGE = 1
    const val API_KEY = Constants.API_KEY
}

object ArgsConstants {
    const val ARGS_DATA = "argsData"
}

class ArgsDataException(override val message: String) : Throwable(message)