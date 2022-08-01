package com.ba6ba.paybackcasestudy.images.presentation

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.ImageLoader
import coil.disk.DiskCache
import coil.load
import coil.memory.MemoryCache
import coil.size.Scale
import coil.transition.CrossfadeTransition
import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.common.ViewState
import com.ba6ba.paybackcasestudy.common.default
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
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.backgroundColor = ContextCompat.getColor(context, R.color.colorText)
    circularProgressDrawable.start()
    load(url) {
        placeholder(circularProgressDrawable)
        error(drawable = ContextCompat.getDrawable(context, R.drawable.ic_placeholder))
        fallback(drawable = ContextCompat.getDrawable(context, R.drawable.ic_placeholder))
        crossfade(true)
        scaleType = ImageView.ScaleType.CENTER_CROP
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
        addView(
            createTagChip(
                context = context,
                chipName = tag.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() })
        )
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
fun SwipeRefreshLayout.onRefreshCallback(onSwipeRefreshListener: OnSwipeRefreshListener) {
    setOnRefreshListener {
        onSwipeRefreshListener.onSwipeRefresh()
        isRefreshing = false
    }
}

@BindingAdapter(value = ["on_query_text_submit_listener"])
fun SearchView.onQueryTextSubmit(onQueryTextSubmit: OnQueryTextSubmit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String?): Boolean {
            onQueryTextSubmit.onSubmit(p0.default)
            return false
        }

        override fun onQueryTextChange(p0: String?): Boolean {
            return false
        }
    })
}

@FunctionalInterface
interface OnQueryTextSubmit {
    fun onSubmit(query: String)
}

@FunctionalInterface
interface OnSwipeRefreshListener {
    fun onSwipeRefresh()
}


@BindingAdapter(value = ["set_default_text"])
fun SearchView.setDefaultText(text: String) {
    setQuery(text, true)
}

@BindingAdapter(value = ["set_view_state_error_message"])
fun AppCompatTextView.setViewStateError(viewState: ViewState<*>) {
    if (viewState is ViewState.Error) {
        text = viewState.uiError.message
    }
}

@BindingAdapter(value = ["navigate_back_on_click"])
fun View.navigateBack(unit: Unit?) {
    setOnClickListener {
        findNavController().navigateUp()
    }
}

