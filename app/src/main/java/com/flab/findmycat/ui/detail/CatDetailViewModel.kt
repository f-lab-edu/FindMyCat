package com.flab.findmycat.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.flab.findmycat.domain.Image
import com.flab.findmycat.network.CatsApi
import com.flab.findmycat.network.CatsApi.Companion.NETWORK_PAGE_SIZE
import com.flab.findmycat.network.ResultOf
import com.flab.findmycat.util.LoadMoreListener
import kotlinx.coroutines.launch

class CatDetailViewModel(
    private val catsApi: CatsApi
) : ViewModel() {
    private val _cats = MutableLiveData<ResultOf<List<Image>>>()
    val cats: LiveData<ResultOf<List<Image>>> = _cats

    private var _page = 0
    var breedId: String = ""
    private val isLast: Boolean = false
    private val isLoading: Boolean get() = _cats.value is ResultOf.Loading
    private val items = _cats.map {
        if (it is ResultOf.Success) it.value
        else emptyList()
    }

    val loadMoreListener = LoadMoreListener {
        if (isLast || isLoading) return@LoadMoreListener
        getCatDetails(breedId)
    }

    init {
        _page = 0
        breedId = ""
        cats.value
    }

    fun getCatDetails(breedIds: String) {
        viewModelScope.launch {
            try {
                val keyword = if (breedId.isEmpty()) breedIds else breedId
                _cats.postValue(ResultOf.Loading)
                val apiResults = catsApi.getSearchCats(keyword, _page, NETWORK_PAGE_SIZE)
                _page++

                _cats.value = ResultOf.Success(apiResults.plus(items.value) as List<Image>)
            } catch (e: Exception) {
                _cats.value = ResultOf.Failure("error${e.message}")
            }
        }
    }
}
