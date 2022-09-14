package com.flab.findmycat.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.flab.findmycat.databinding.FragmentCatsBinding
import com.flab.findmycat.domain.Cat
import com.flab.findmycat.network.ResultOf
import com.flab.findmycat.util.LoadMoreListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CatsFragment : Fragment() {
    private val viewModel: CatsViewModel by sharedViewModel()
    private val listAdapter by lazy {
        CatsAdapter(this, object : CatClickListener {
            override fun onClick(cat: Cat) {
                val action = CatsFragmentDirections.actionFragmentCatsToFragmentCatDetail(cat.id)
                findNavController().navigate(action)
            }
        }, object : LoadMoreListener {
            override fun loadMore() {
                viewModel.getCats()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCatsBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.photosGrid.adapter = listAdapter

        viewModel.catsResultOf.observe(viewLifecycleOwner) { results ->
            when (results) {
                is ResultOf.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is ResultOf.Success -> {
                    listAdapter.submitList(results.value)
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
