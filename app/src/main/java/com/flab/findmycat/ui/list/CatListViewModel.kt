package com.flab.findmycat.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flab.findmycat.domain.Cat
import com.flab.findmycat.network.CatsApi
import kotlinx.coroutines.launch

class CatListViewModel(
    private val catsApi: CatsApi
) : ViewModel() {
    private val _cats = MutableLiveData<List<Cat>>()
    val cats: LiveData<List<Cat>> = _cats

    var mPage: Int = 0
    var isLast: Boolean = false

    init {
        getCats()
    }

    fun getCats() {
        viewModelScope.launch {
            try {
                val list = catsApi.getCats(mPage, NETWORK_PAGE_SIZE)
                mPage += 1
                _cats.value = _cats.value?.plus(list) ?: list
                if (list.isEmpty()) {
                    isLast = true
                }
            } catch (e: Exception) {
                _cats.value = listOf()
            }
        }
    }
}
