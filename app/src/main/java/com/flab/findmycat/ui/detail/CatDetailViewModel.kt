package com.flab.findmycat.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flab.findmycat.domain.Image
import com.flab.findmycat.network.CatApi
import kotlinx.coroutines.launch

class CatDetailViewModel : ViewModel() {
    private val _cats = MutableLiveData<List<Image>>()
    val cats: LiveData<List<Image>> = _cats

    fun getCatDetails(query: String) {
        viewModelScope.launch {
            try {
                _cats.value = CatApi.retrofitService.getSearchCats(query)
            } catch (e: Exception) {
                _cats.value = emptyList()
            }
        }
    }
}
