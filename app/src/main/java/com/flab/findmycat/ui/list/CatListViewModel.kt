package com.flab.findmycat.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flab.findmycat.domain.Cat
import com.flab.findmycat.network.CatApi
import kotlinx.coroutines.launch

class CatListViewModel : ViewModel() {
    private val _cats = MutableLiveData<List<Cat>>()
    val cats: LiveData<List<Cat>> = _cats

    init {
        getCats()
    }

    private fun getCats() {
        viewModelScope.launch {
            try {
                _cats.value = CatApi.retrofitService.getCats(INITIAL_PAGE_INDEX, NETWORK_PAGE_SIZE)
            } catch (e: Exception) {
                _cats.value = listOf()
            }
        }
    }
}
