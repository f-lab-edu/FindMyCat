package com.flab.findmycat.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flab.findmycat.domain.Image
import com.flab.findmycat.network.CatsApi
import com.flab.findmycat.network.ResultOf
import kotlinx.coroutines.launch

class CatDetailViewModel(
    private val catsApi: CatsApi
) : ViewModel() {
    private val _cats = MutableLiveData<ResultOf<List<Image>>>()
    val cats: LiveData<ResultOf<List<Image>>> = _cats

    fun getCatDetails(query: String) {
        viewModelScope.launch {
            try {
                _cats.postValue(ResultOf.Loading)

                val apiResults = catsApi.getSearchCats(query)
                _cats.value = ResultOf.Success(apiResults)
            } catch (e: Exception) {
                _cats.value = ResultOf.Failure("error${e.message}")
            }
        }
    }
}
