package com.ba6ba.paybackcasestudy.images.presentation

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.ba6ba.paybackcasestudy.core.BaseViewHolder
import com.ba6ba.paybackcasestudy.common.inflater
import com.ba6ba.paybackcasestudy.databinding.ImageLoadStateItemBinding

class ImageLoadStateAdapter : LoadStateAdapter<LoadingStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.onBind(loadState, 0)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        return LoadingStateViewHolder(
            ImageLoadStateItemBinding.inflate(parent.inflater, parent, false)
        )
    }
}

class LoadingStateViewHolder(private val binding: ImageLoadStateItemBinding) :
    BaseViewHolder<LoadState>(binding.root) {

    override fun onBind(item: LoadState, position: Int) {
        binding.loader.toggleVisibility(item is LoadState.Loading)
    }
}