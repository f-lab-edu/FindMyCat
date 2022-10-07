package com.flab.findmycat.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flab.findmycat.domain.Cat
import com.flab.findmycat.domain.Image
import com.flab.findmycat.network.CatsApi
import com.flab.findmycat.network.CatsApi.Companion.NETWORK_PAGE_SIZE
import com.flab.findmycat.util.LoadMoreListener
import kotlinx.coroutines.launch

data class CatListUiModel(val index: Int, val image: Image?)

class CatsViewModel(
    private val catsApi: CatsApi
) : ViewModel() {

    private var page = 0

    private var isLast: Boolean = false
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _items = MutableLiveData<List<Cat>>()
    val items: LiveData<List<Cat>> = _items

    val loadMoreListener = LoadMoreListener {
        if (isLast || _isLoading.value == true) return@LoadMoreListener
        getCats()
    }

    init {
        page = 0
        getCats()
    }

    private val current get() = _items.value ?: emptyList()

    private fun getCats() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val list = catsApi.getCats(page++, NETWORK_PAGE_SIZE)
                isLast = list.size < NETWORK_PAGE_SIZE
                _items.value = current + list
            } catch (e: Exception) {
                //라이브 이벤트 or 쉐어드 플로우
                // _catsResultOf.postValue(ResultOf.Failure("error${e.message}"))
            } finally {
                _isLoading.value = false
            }
        }
    }
}
