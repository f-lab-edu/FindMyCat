package com.flab.findmycat.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flab.findmycat.domain.Cat
import com.flab.findmycat.network.CatsApi
import com.flab.findmycat.network.NETWORK_PAGE_SIZE
import com.flab.findmycat.network.ResultOf
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsApi: CatsApi
) : ViewModel() {
    private val _catsResultOf = MutableLiveData<ResultOf<List<Cat>>>()
    val catsResultOf: LiveData<ResultOf<List<Cat>>> = _catsResultOf
    private val _cats = MutableLiveData<List<Cat>>()

    private val _page = MutableLiveData<Int>()

    init {
        _page.value = 0
        getCats()
    }

    fun getCats() {
        viewModelScope.launch {
            try {
                _catsResultOf.postValue(ResultOf.Loading)
                val list = catsApi.getCats(_page.value ?: 0, NETWORK_PAGE_SIZE)
                _page.postValue(_page.value?.plus(1))

                val results = _cats.value?.plus(list) ?: list
                _cats.value = results

                _catsResultOf.postValue(ResultOf.Success(results))
            } catch (e: Exception) {
                _catsResultOf.postValue(ResultOf.Failure("error${e.message}"))
            }
        }
    }
}
