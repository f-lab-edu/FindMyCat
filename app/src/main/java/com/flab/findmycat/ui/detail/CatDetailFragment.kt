package com.flab.findmycat.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.flab.findmycat.databinding.FragmentCatDetailBinding
import com.flab.findmycat.network.ResultOf
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CatDetailFragment : Fragment() {
    private val viewModel: CatDetailViewModel by sharedViewModel()
    private val args: CatDetailFragmentArgs by navArgs()

    private val detailAdapter by lazy {
        CatDetailAdapter(this,  viewModel.loadMoreListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCatDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel.breedId = args.breedId
        binding.viewModel = viewModel
        binding.listCats.adapter = detailAdapter

        viewModel.apply {
            getCatDetails(args.breedId)
        }

        viewModel.cats.observe(viewLifecycleOwner) { results ->
            when (results) {
                is ResultOf.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is ResultOf.Success -> {
                    detailAdapter.submitList(results.value)
                    binding.progressbar.visibility = View.INVISIBLE
                }
                is ResultOf.Failure -> {
                    binding.progressbar.visibility = View.INVISIBLE
                    Toast.makeText(requireActivity(), results.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }
}
