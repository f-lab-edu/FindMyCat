package com.flab.findmycat.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.flab.findmycat.data.database.Status
import com.flab.findmycat.databinding.FragmentSearchBinding
import com.flab.findmycat.ui.interfaces.IProgressBarActivity
import com.flab.findmycat.ui.interfaces.IProgressBarFragment
import com.flab.findmycat.ui.interfaces.ISearchListenerActivity
import com.flab.findmycat.ui.interfaces.ISearchListenerFragment
import com.flab.findmycat.utils.toastS
import kotlinx.android.synthetic.main.fragment_search.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SearchFragment : Fragment(), KodeinAware, IProgressBarFragment, ISearchListenerFragment {

    override val kodein by kodein()
    private val factory: SearchViewModelFactory by instance()

    private val args: SearchFragmentArgs by navArgs()

    private lateinit var searchQuery: String
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchAdapter: SearchAdapter

    override fun showProgressBar() = (requireActivity() as IProgressBarActivity).show()
    override fun hideProgressBar() = (requireActivity() as IProgressBarActivity).hide()

    override fun doSearch(searchQuery: String) {
        this.searchQuery = searchQuery
        getData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchQuery = args.searchQuery

        (requireActivity() as ISearchListenerActivity).registerSearchFragment(this)
        (requireActivity() as ISearchListenerActivity).setSearchText(searchQuery)

        viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
        viewModel.repos.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.Loading -> showProgressBar()
                is Status.Success -> {
                    hideProgressBar()
                    if (status.data.isNotEmpty() && status.data != searchAdapter.repos) {
                        searchAdapter.apply {
                            this.repos = status.data
                        }
                    }
                }
                is Status.Error -> {
                    requireContext().toastS(status.errorMessage)
                    hideProgressBar()
                }
            }
        }
        getData()
    }

    private fun bindUI() {
        searchAdapter = SearchAdapter()
        recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = searchAdapter
        }
    }

    private fun getData() = viewModel.getRepos(searchQuery)

    override fun onResume() {
        super.onResume()
        (requireActivity() as ISearchListenerActivity).showSearchView(true)
    }
}
