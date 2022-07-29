package com.ba6ba.paybackcasestudy.images.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.common.dataBinding
import com.ba6ba.paybackcasestudy.databinding.FragmentImageListingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageListingFragment : Fragment(R.layout.fragment_image_listing) {

    private val binding: FragmentImageListingBinding by dataBinding(FragmentImageListingBinding::bind)
    private val imageListingViewModel: ImageListingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = imageListingViewModel
        imageListingViewModel.setPersistedDisplayMode()
    }
}