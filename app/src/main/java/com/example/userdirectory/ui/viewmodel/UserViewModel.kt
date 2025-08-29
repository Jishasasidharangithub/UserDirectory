package io.proximety.hilitemall.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.proximety.hilitemall.model.request.ApplyCouponBody
import io.proximety.hilitemall.model.response.GenericResponse
import io.proximety.hilitemall.network.NetworkResult
import io.proximety.hilitemall.network.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplyCouponViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _applyCoupon = MutableLiveData<NetworkResult<GenericResponse?>>()
    val applyCoupon: MutableLiveData<NetworkResult<GenericResponse?>> get() = _applyCoupon

    fun applyCoupon(body: ApplyCouponBody) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.applyCoupon(body).collect { result ->
                _applyCoupon.postValue(result)
            }
        }
    }
}