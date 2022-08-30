package com.flab.findmycat.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.flab.findmycat.databinding.FragmentCatListBinding
import com.flab.findmycat.domain.Cat

class CatListFragment : Fragment() {
    private val viewModel: CatListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCatListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.photosGrid.adapter = CatListAdapter(this, object : CatClickListener {
            override fun onClick(cat: Cat) {
                val action =
                    CatListFragmentDirections.actionFragmentCatListToFragmentCatDetail(cat.id)
                findNavController().navigate(action)
            }
        })
        return binding.root
    }
}
