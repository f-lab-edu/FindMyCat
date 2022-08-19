package kr.f.lab.findmycat.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.f.lab.findmycat.domain.Cat
import kr.f.lab.findmycat.network.ApiStatus
import kr.f.lab.findmycat.network.CatApi

class CatListViewModel : ViewModel() {
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _cats = MutableLiveData<List<Cat>>()
    val cats: LiveData<List<Cat>> = _cats

    init {
        getCats()
    }

    private fun getCats() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _cats.value = CatApi.retrofitService.getCats(0, 1)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _cats.value = listOf()
            }
        }
    }
}
