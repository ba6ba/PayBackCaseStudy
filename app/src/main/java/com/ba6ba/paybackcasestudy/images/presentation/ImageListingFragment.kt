package com.ba6ba.paybackcasestudy.images.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.common.ArgsConstants
import com.ba6ba.paybackcasestudy.common.dataBinding
import com.ba6ba.paybackcasestudy.common.showConfirmationDialog
import com.ba6ba.paybackcasestudy.databinding.FragmentImageListingBinding
import com.ba6ba.paybackcasestudy.images.data.ImageItemUiData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageListingFragment : Fragment(R.layout.fragment_image_listing) {

    private val binding: FragmentImageListingBinding by dataBinding(FragmentImageListingBinding::bind)
    private val imageListingViewModel: ImageListingViewModel by viewModels()
    private val imageListingAdapter: ImageListingAdapter by lazy {
        ImageListingAdapter(::onImageClickListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBindings()
        imageListingViewModel.setPersistedDisplayMode()
        listenAdapterUpdates()
    }

    private fun setBindings() {
        binding.run {
            viewModel = imageListingViewModel
            adapter = imageListingAdapter
        }
    }

    private fun listenAdapterUpdates() {
        lifecycleScope.launch {
            imageListingViewModel.pagingDataFlow.collectLatest { pagingData ->
                imageListingAdapter.submitData(pagingData)
            }
        }
        imageListingAdapter.addLoadStateListener { combinedLoadStates ->
            imageListingViewModel.processCombinedStates(combinedLoadStates)
        }
    }

    private fun onImageClickListener(imageItemUiData: ImageItemUiData) {
        showConfirmationDialog {
            findNavController().navigate(R.id.image_listing_to_image_details, Bundle().apply {
                putParcelable(ArgsConstants.ARGS_DATA, imageListingViewModel.getArgsData(imageItemUiData))
            })
        }
    }

}