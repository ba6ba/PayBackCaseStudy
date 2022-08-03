package com.ba6ba.paybackcasestudy.images.presentation

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ba6ba.paybackcasestudy.core.BaseViewHolder
import com.ba6ba.paybackcasestudy.common.inflater
import com.ba6ba.paybackcasestudy.databinding.ImageListItemBinding
import com.ba6ba.paybackcasestudy.images.data.ImageItemUiData

class ImageListingAdapter(private val itemClickListener: (ImageItemUiData) -> Unit) :
    PagingDataAdapter<ImageItemUiData, HomeItemViewHolder>(ImageItemDiffUtil) {

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        getItem(position)?.let { data ->
            holder.onBind(data, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        return HomeItemViewHolder(
            ImageListItemBinding.inflate(parent.inflater, parent, false),
            itemClickListener
        )
    }
}

class HomeItemViewHolder(
    private val binding: ImageListItemBinding,
    private val itemClickListener: (ImageItemUiData) -> Unit
) : BaseViewHolder<ImageItemUiData>(binding.root) {
    override fun onBind(item: ImageItemUiData, position: Int) {
        binding.run {
            uiData = item
            rootItem.setOnClickListener {
                itemClickListener(item)
            }
        }
    }
}

object ImageItemDiffUtil : DiffUtil.ItemCallback<ImageItemUiData>() {

    override fun areItemsTheSame(oldItem: ImageItemUiData, newItem: ImageItemUiData): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }

    override fun areContentsTheSame(oldItem: ImageItemUiData, newItem: ImageItemUiData): Boolean {
        return oldItem == newItem
    }
}