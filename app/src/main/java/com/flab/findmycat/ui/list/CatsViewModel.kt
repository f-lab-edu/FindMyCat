package com.flab.findmycat.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.flab.findmycat.domain.Cat
import com.flab.findmycat.domain.Image
import com.flab.findmycat.network.CatsApi
import com.flab.findmycat.network.CatsApi.Companion.NETWORK_PAGE_SIZE
import com.flab.findmycat.network.ResultOf
import com.flab.findmycat.util.LoadMoreListener
import kotlinx.coroutines.launch

data class CatListUiModel(val index: Int, val image: Image?)

class CatsViewModel(
    private val catsApi: CatsApi
) : ViewModel() {
    private val _catsResultOf = MutableLiveData<ResultOf<List<Cat>>>()
    val catsResultOf: LiveData<ResultOf<List<Cat>>> = _catsResultOf

    private var page = 0

    private var isLast: Boolean = false
    private val isLoading: Boolean get() = _catsResultOf.value is ResultOf.Loading
    private val items = _catsResultOf.map {
        if (it is ResultOf.Success) it.value
        else emptyList()
    }

    val loadMoreListener = LoadMoreListener {
        if (isLast || isLoading) return@LoadMoreListener
        getCats()
    }

    init {
        page = 0
        getCats()
    }

    private fun getCats() {
        viewModelScope.launch {
            try {
                _catsResultOf.value = ResultOf.Loading
                val list = catsApi.getCats(page, NETWORK_PAGE_SIZE)
                page++
                isLast = list.isEmpty()
                _catsResultOf.value = ResultOf.Success(list.plus(items.value) as List<Cat>)
            } catch (e: Exception) {
                _catsResultOf.postValue(ResultOf.Failure("error${e.message}"))
            }
        }
    }
}
