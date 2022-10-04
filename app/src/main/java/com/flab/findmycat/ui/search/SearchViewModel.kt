package com.flab.findmycat.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flab.findmycat.data.database.Status
import com.flab.findmycat.data.database.model.Repo
import com.flab.findmycat.data.repository.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: SearchRepository
) : ViewModel() {

    private val _repos = MutableLiveData<Status<List<Repo>>>()
    val repos: LiveData<Status<List<Repo>>> get() = _repos

    fun getRepos(searchQuery: String) {
        viewModelScope.launch {
            repository.getRepos(searchQuery).collect {
                _repos.value = it
            }
        }
    }
}
