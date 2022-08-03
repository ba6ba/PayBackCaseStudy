package com.ba6ba.paybackcasestudy.images.presentation

import androidx.lifecycle.*
import androidx.paging.*
import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.common.*
import com.ba6ba.paybackcasestudy.images.data.ImageDetailArgsData
import com.ba6ba.paybackcasestudy.images.data.ImageItemUiData
import com.ba6ba.paybackcasestudy.images.domain.FetchSavedQueryUseCase
import com.ba6ba.paybackcasestudy.images.domain.ImageListingUseCase
import com.ba6ba.paybackcasestudy.images.domain.RefreshSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListingViewModel @Inject constructor(
    private val lightDarkModeManager: LightDarkModeManager,
    private val imageListingUseCase: ImageListingUseCase,
    private val imageUiDataTransformer: ImageUiDataTransformer,
    private val refreshSearchUseCase: RefreshSearchUseCase,
    private val fetchSavedQueryUseCase: FetchSavedQueryUseCase
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

    val onQueryTextChange: StateFlow<String>
        get() = _onQueryTextChange
    private val _onQueryTextChange: MutableStateFlow<String> by lazy {
        MutableStateFlow(EMPTY_STRING)
    }

    init {
        viewModelScope.launch {
            val query = fetchSavedQueryUseCase()
            _onQueryTextChange.value = query
        }
    }

    val refreshAdapter: LiveData<Unit?>
        get() = _refreshAdapter
    private val _refreshAdapter: MutableLiveData<Unit?> by lazy {
        MutableLiveData(null)
    }

    val pagingData: Flow<PagingData<ImageItemUiData>> by lazy {
        _onQueryTextChange
            .filter { it.isNotEmpty() }
            .flatMapLatest { query ->
                imageListingUseCase(query).map { pagingData ->
                    pagingData.map { data ->
                        imageUiDataTransformer.transform(data)
                    }
                }
            }.cachedIn(viewModelScope)
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

    fun getArgsData(imageItemUiData: ImageItemUiData): ImageDetailArgsData {
        return ImageDetailArgsData(
            previewUrl = imageItemUiData.imageUrl,
            userName = imageItemUiData.userName,
            tags = imageItemUiData.metadata.tags,
            likes = imageItemUiData.metadata.likes,
            comments = imageItemUiData.metadata.comments,
            downloads = imageItemUiData.metadata.downloads,
            hdImageUrl = imageItemUiData.metadata.hdImageUrl
        )
    }

    val onRefresh = object : OnSwipeRefreshListener {
        override fun onSwipeRefresh() {
            refreshData {
                _refreshAdapter.value = Unit
            }
        }
    }

    val onQueryTextSubmit: OnQueryTextSubmit by lazy {
        object : OnQueryTextSubmit {
            override fun onSubmit(query: String) {
                if (_onQueryTextChange.value != query) {
                    refreshData {
                        _onQueryTextChange.value = query
                    }
                }
            }
        }
    }

    private fun refreshData(completed: () -> Unit) {
        viewModelScope.launch {
            refreshSearchUseCase()
            completed()
        }
    }

    fun clearRefreshAdapterLiveData() {
        _refreshAdapter.value = null
    }
}