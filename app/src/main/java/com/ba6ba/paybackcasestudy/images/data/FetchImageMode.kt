package com.ba6ba.paybackcasestudy.images.data

sealed interface FetchImageMode {
    object Database : FetchImageMode
    data class Remote(val page: Int, val query: String) : FetchImageMode
}