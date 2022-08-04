package com.ba6ba.paybackcasestudy.images.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ba6ba.paybackcasestudy.common.ArgsConstants
import com.ba6ba.paybackcasestudy.common.ArgsDataException
import com.ba6ba.paybackcasestudy.images.data.ImageDetailArgsData
import com.ba6ba.paybackcasestudy.images.data.ImageItemDetailUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val imageDetailUiDataTransformer: ImageDetailUiDataTransformer
) : ViewModel() {

    private val imageDetailArgsData: ImageDetailArgsData by lazy {
        savedStateHandle.get<ImageDetailArgsData>(ArgsConstants.ARGS_DATA)
            ?: throw ArgsDataException(ImageDetailArgsData::class.java.simpleName)
    }

    val imageDetailUiData: StateFlow<ImageItemDetailUiData>
        get() = _imageDetailUiData
    private val _imageDetailUiData: MutableStateFlow<ImageItemDetailUiData> by lazy {
        MutableStateFlow(imageDetailUiDataTransformer.transform(imageDetailArgsData))
    }

}