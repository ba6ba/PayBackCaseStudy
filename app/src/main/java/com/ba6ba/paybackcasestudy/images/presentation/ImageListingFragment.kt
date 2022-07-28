package com.ba6ba.paybackcasestudy.images.presentation

import androidx.fragment.app.Fragment
import com.ba6ba.paybackcasestudy.R
import com.ba6ba.paybackcasestudy.base.dataBinding
import com.ba6ba.paybackcasestudy.databinding.FragmentImageListingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageListingFragment : Fragment(R.layout.fragment_image_listing) {

    private val binding: FragmentImageListingBinding by dataBinding(FragmentImageListingBinding::bind)

}