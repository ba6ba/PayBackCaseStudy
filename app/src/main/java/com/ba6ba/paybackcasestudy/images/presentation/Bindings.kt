package com.ba6ba.paybackcasestudy.images.presentation

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
    val radius = 20f
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
    }.isDisposed
}

@BindingAdapter(value = ["add_tags"])
fun ChipGroup.addChips(tags: List<String>) {
    removeAllViews()
    tags.forEach { tag ->
        addView(createTagChip(context = context, chipName = tag.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }))
    }
}

private fun createTagChip(context: Context, chipName: String): Chip {
    return Chip(context).apply {
        text = chipName
        setChipBackgroundColorResource(R.color.very_light_grey)
        isCloseIconVisible = false
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

@BindingAdapter(value = ["on_refresh"])
fun SwipeRefreshLayout.onRefreshCallback(onRefresh: () -> Unit) {
    setOnRefreshListener {
        onRefresh()
        isRefreshing = false
    }
}