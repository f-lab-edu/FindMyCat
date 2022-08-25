package com.flab.findmycat.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flab.findmycat.domain.DetailCat
import com.flab.findmycat.network.CatApi
import kotlinx.coroutines.launch

class CatDetailViewModel : ViewModel() {
    private val _catDetail = MutableLiveData<DetailCat>()
    val catDetail: LiveData<DetailCat> = _catDetail

    fun getCatDetail(query: String) {
        viewModelScope.launch {
            try {
                _catDetail.value = CatApi.retrofitService.getSearchCat(query)[0]
            } catch (e: Exception) {
                _catDetail.value = null
            }
        }
    }
}