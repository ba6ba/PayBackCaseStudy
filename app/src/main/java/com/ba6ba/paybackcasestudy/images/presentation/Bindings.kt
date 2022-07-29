package com.ba6ba.paybackcasestudy.images.presentation

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.ba6ba.paybackcasestudy.common.ViewState

@BindingAdapter(value = ["toggle_visibility"])
fun View.toggleVisibility(value: Boolean) {
    isVisible = value
}

@BindingAdapter(value = ["show_on_loading"])
fun View.showOnLoading(viewState: ViewState<*>) {
    toggleVisibility(viewState is ViewState.Loading)
}

@BindingAdapter(value = ["show_on_error"])
fun View.showOnError(viewState: ViewState<*>) {
    toggleVisibility(viewState is ViewState.Error)
}

@BindingAdapter(value = ["show_on_success"])
fun View.showOnSuccess(viewState: ViewState<*>) {
    toggleVisibility(viewState is ViewState.Success<*>)
}

@BindingAdapter(value = ["image_drawable_resource"])
fun AppCompatImageView.updateImageDrawable(imageDrawableResource: Int) {
    setImageResource(imageDrawableResource)
}