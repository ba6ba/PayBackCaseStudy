package com.ba6ba.paybackcasestudy.images.presentation

import androidx.lifecycle.*
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.common.*
import com.ba6ba.paybackcasestudy.images.data.ImageItemUiData
import com.ba6ba.paybackcasestudy.images.domain.ImageListingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class ImageListingViewModel @Inject constructor(
    private val lightDarkModeManager: LightDarkModeManager,
    private val imageListingUseCase: ImageListingUseCase,
    private val stringsResourceManager: StringsResourceManager
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

    val onQueryTextChange: MutableLiveData<String>
        get() = _onQueryTextChange
    private val _onQueryTextChange: MutableLiveData<String> by lazy {
        MutableLiveData(stringsResourceManager.getString(R.string.default_query))
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

    fun collectPagingData(query: String) =
        imageListingUseCase(query).cachedIn(viewModelScope)

    fun processCombinedStates(combinedLoadStates: CombinedLoadStates) {
        _viewStateFlow.value = if (hasErrorInAppendOrPrepend(combinedLoadStates)) {
            ViewState.Error(UiError(message = getErrorFromAppendOrPrepend(combinedLoadStates)))
        } else {
            when (val state = combinedLoadStates.refresh) {
                is LoadState.Loading -> ViewState.Loading
                is LoadState.Error ->
                    ViewState.Error(UiError(message = state.error.message.default))
                is LoadState.NotLoading -> ViewState.Success(Unit)
            }
        }
    }

    private fun hasErrorInAppendOrPrepend(combinedLoadStates: CombinedLoadStates): Boolean {
        return when {
            combinedLoadStates.prepend is LoadState.Error -> true
            combinedLoadStates.append is LoadState.Error -> true
            combinedLoadStates.source.append is LoadState.Error -> true
            combinedLoadStates.source.prepend is LoadState.Error -> true
            else -> false
        }
    }

    private fun getErrorFromAppendOrPrepend(combinedLoadStates: CombinedLoadStates): String {
        return when {
            combinedLoadStates.prepend is LoadState.Error ->
                (combinedLoadStates.prepend as LoadState.Error).error.message

            combinedLoadStates.append is LoadState.Error ->
                (combinedLoadStates.append as LoadState.Error).error.message

            combinedLoadStates.source.append is LoadState.Error ->
                (combinedLoadStates.source.append as LoadState.Error).error.message

            combinedLoadStates.source.prepend is LoadState.Error ->
                (combinedLoadStates.source.prepend as LoadState.Error).error.message

            else -> EMPTY_STRING
        }.default
    }

    val onQueryTextSubmit: OnQueryTextSubmit by lazy {
        object : OnQueryTextSubmit {
            override fun onSubmit(query: String) {
                _onQueryTextChange.value = query
            }
        }
    }
}