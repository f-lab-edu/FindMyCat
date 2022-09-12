package com.flab.findmycat.ui.list

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.flab.findmycat.databinding.FragmentCatListBinding
import com.flab.findmycat.domain.Cat
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CatListFragment : Fragment() {
    private val viewModel: CatListViewModel by sharedViewModel()
    private val listAdapter by lazy {
        CatListAdapter(this, object : CatClickListener {
            override fun onClick(cat: Cat) {
                val action =
                    CatListFragmentDirections.actionFragmentCatListToFragmentCatDetail(cat.id)
                findNavController().navigate(action)
            }
        })
    }
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCatListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.photosGrid.adapter = listAdapter
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.idNestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    if (count < NETWORK_PAGE_SIZE) {
                        viewModel.getCats(count++)
                    }
                }
            })
        }

        viewModel.cats.observe(viewLifecycleOwner) {
            listAdapter.submitList(it.toMutableList())
        }

        return binding.root
    }
}
