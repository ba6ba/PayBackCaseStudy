package com.ba6ba.paybackcasestudy.images.presentation

import androidx.lifecycle.ViewModel
import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.common.LightDarkModeManager
import com.ba6ba.paybackcasestudy.common.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ImageListingViewModel @Inject constructor(
    private val lightDarkModeManager: LightDarkModeManager,
) : ViewModel() {

    val viewStateFlow: StateFlow<ViewState<Unit>>
        get() = _viewStateFlow
    private val _viewStateFlow: MutableStateFlow<ViewState<Unit>> by lazy {
        MutableStateFlow(ViewState.Idle)
    }

    val dayNightIconResource: StateFlow<Int>
        get() = _dayNightIconResource
    private val _dayNightIconResource: MutableStateFlow<Int> by lazy {
        MutableStateFlow(R.drawable.ic_day_mode)
    }

    fun setPersistedDisplayMode() {
        lightDarkModeManager.setCurrentMode()
        updateDayNightIcon()
    }

    private fun updateDayNightIcon() {
        _dayNightIconResource.value =
            if (lightDarkModeManager.isDarkModeEnabled()) R.drawable.ic_night_mode else R.drawable.ic_day_mode
    }

    fun onDayNightButtonClick() {
        lightDarkModeManager.toggle()
        updateDayNightIcon()
    }
}