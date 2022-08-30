package com.flab.findmycat.ui.list

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.flab.findmycat.databinding.FragmentCatListBinding
import com.flab.findmycat.domain.Cat

class CatListFragment : Fragment() {
    private val viewModel: CatListViewModel by viewModels()

    val listAdapter by lazy {
        CatListAdapter(this, object : CatClickListener {
            override fun onClick(cat: Cat) {
                val action =
                    CatListFragmentDirections.actionFragmentCatListToFragmentCatDetail(cat.id)
                findNavController().navigate(action)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCatListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.photosGrid.adapter = listAdapter
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.photosGrid.addOnScrollListener(object : RecyclerViewPaginator(binding.photosGrid),
                View.OnScrollChangeListener {
                override val isLastPage: Boolean
                    get() = viewModel.isLast

                override fun loadMore(page: Long, count: Long) {
                    viewModel.getCats(page.toInt())
                }

                override fun onScrollChange(v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                    Log.d("TEST", "onScrollChange()")
                    super.onScrolled(v as RecyclerView, scrollX, scrollY)
                }
            })
        }

        viewModel.cats.observe(viewLifecycleOwner) {
            listAdapter.submitList(it.toMutableList())
        }

        return binding.root
    }
}
