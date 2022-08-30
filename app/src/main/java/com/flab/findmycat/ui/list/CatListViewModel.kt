package com.flab.findmycat.ui.list

import android.util.Log
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

    private var page : Int = INITIAL_PAGE_INDEX

    var isLast : Boolean = false

    init {
        getCats(page)
    }

    fun getCats(page : Int) {
        viewModelScope.launch {
            try {
                Log.d("TEST", "getCats ${page} isLast : $isLast")
                val list = CatApi.retrofitService.getCats(page, NETWORK_PAGE_SIZE)
                _cats.value = _cats.value?.plus(list) ?: list
                if (list.isEmpty()){
                    isLast = true
                }
            } catch (e: Exception) {
                _cats.value = listOf()
            }
        }
    }
}
