package com.ba6ba.paybackcasestudy.images.data

sealed interface PageFetchState {
    object Database : PageFetchState
    object Remote : PageFetchState
}