package com.flab.findmycat.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.flab.findmycat.databinding.FragmentHomeBinding
import com.flab.findmycat.ui.interfaces.IProgressBarActivity
import com.flab.findmycat.ui.interfaces.IProgressBarFragment
import com.flab.findmycat.ui.interfaces.ISearchListenerActivity

class HomeFragment : Fragment(), IProgressBarFragment {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment(query.trim())
                    findNavController().navigate(action)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        hideProgressBar()
        (requireActivity() as ISearchListenerActivity).showSearchView(false)
    }

    override fun showProgressBar() = (requireActivity() as IProgressBarActivity).show()

    override fun hideProgressBar() = (requireActivity() as IProgressBarActivity).hide()
}
