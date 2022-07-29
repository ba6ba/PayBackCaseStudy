package com.ba6ba.paybackcasestudy.images.presentation

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import coil.transition.CrossfadeTransition
import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.common.ViewState
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

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

@BindingAdapter(value = ["load_image"])
fun ImageView.loadImage(url: String?) {
    load(url) {
        placeholder(ContextCompat.getDrawable(context, R.drawable.ripple_white_background))
        error(drawable = ContextCompat.getDrawable(context, R.drawable.ripple_white_background))
        fallback(drawable = ContextCompat.getDrawable(context, R.drawable.ripple_white_background))
        crossfade(true)
        transition(CrossfadeTransition())
        target(
            onSuccess = { drawable ->
                setImageDrawable(drawable)
            },
            onError = { errorDrawable ->
                setImageDrawable(errorDrawable)
            },
            onStart = { placeholder ->
                setImageDrawable(placeholder)
            }
        )
        transformations(RoundedCornersTransformation(10f, 10f, 10f, 10f))
    }.isDisposed
}

@BindingAdapter(value = ["add_tags"])
fun ChipGroup.addChips(tags: List<String>) {
    tags.forEach { tag ->
        addView(createTagChip(context = context, chipName = tag))
    }
}

private fun createTagChip(context: Context, chipName: String): Chip {
    return Chip(context).apply {
        text = chipName
        setChipBackgroundColorResource(R.color.light_grey)
        isCloseIconVisible = true
        setTextColor(ContextCompat.getColor(context, android.R.color.black))
    }
}

@BindingAdapter(value = ["set_paging_adapter"])
fun RecyclerView.setPagingAdapter(homeItemAdapter: ImageListingAdapter) {
    homeItemAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
    adapter = homeItemAdapter.withLoadStateFooter(ImageLoadStateAdapter())
}

@BindingAdapter(value = ["add_item_decoration"])
fun RecyclerView.addItemDecoration(drawable: Drawable? = null) {
    addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
}
