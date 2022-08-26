package com.flab.findmycat.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.flab.findmycat.databinding.FragmentCatDetailBinding

class CatDetailFragment : Fragment() {

    private val viewModel: CatDetailViewModel by viewModels()
    private val safeArgs: CatDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCatDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.apply {
            getCatDetail(safeArgs.breedId)
        }

        return binding.root
    }

}