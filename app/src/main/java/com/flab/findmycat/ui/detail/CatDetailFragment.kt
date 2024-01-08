package com.flab.findmycat.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.flab.findmycat.databinding.FragmentCatDetailBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CatDetailFragment : Fragment() {
    private val viewModel: CatDetailViewModel by sharedViewModel()
    private val safeArgs: CatDetailFragmentArgs by navArgs()

    private val detailAdapter by lazy {
        CatDetailAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCatDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.gridCats.adapter = detailAdapter
        viewModel.apply {
            getCatDetails(safeArgs.breedId)
        }
        return binding.root
    }
}
