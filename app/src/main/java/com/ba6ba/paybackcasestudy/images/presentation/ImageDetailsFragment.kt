package com.ba6ba.paybackcasestudy.images.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.common.dataBinding
import com.ba6ba.paybackcasestudy.databinding.FragmentImageDetailsBinding
import com.ba6ba.paybackcasestudy.databinding.FragmentImageListingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageDetailsFragment : Fragment(R.layout.fragment_image_details) {

    private val binding: FragmentImageDetailsBinding by dataBinding(FragmentImageDetailsBinding::bind)
    private val imageDetailsViewModel: ImageDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = imageDetailsViewModel
    }
}