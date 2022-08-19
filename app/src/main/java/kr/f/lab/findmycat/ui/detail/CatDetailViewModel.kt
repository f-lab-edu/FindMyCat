package kr.f.lab.findmycat.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.f.lab.findmycat.domain.Cat
import kr.f.lab.findmycat.network.ApiStatus
import kr.f.lab.findmycat.network.CatApi

class CatDetailViewModel : ViewModel() {
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _cat = MutableLiveData<Cat?>()
    val cat: LiveData<Cat?> = _cat

    init {
        _status.value = ApiStatus.LOADING
    }

    fun getCat(query : String) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _cat.value = CatApi.retrofitService.getSearchCat(query)[0]
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _cat.value = null
            }
        }
    }
}
