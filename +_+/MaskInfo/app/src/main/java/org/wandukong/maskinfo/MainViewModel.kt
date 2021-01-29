package org.wandukong.maskinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _maskList = MutableLiveData<List<ResponseStoreData.Store>>()
    val maskList: LiveData<List<ResponseStoreData.Store>> get() = _maskList

    private val _latitude = MutableLiveData<Double>()
    val latitude: LiveData<Double> get() = _latitude

    private val _longitude = MutableLiveData<Double>()
    val longitude: LiveData<Double> get() = _longitude

    private val _loadingData = MutableLiveData<Boolean>(true)
    val loadingData: LiveData<Boolean> get() = _loadingData

    private val service = MaskServiceImpl.service

    fun fetchMaskInfo() {
        setLoadingData(true)
        viewModelScope.launch {
            val maskList = service.getMaskInfo(latitude.value!!, longitude.value!!)
            // getMaskInfo가 suspend 함수로 구현되어 있다.
            // 비동기 함수인 suspend 함수는 다른 suspend 함수, 혹은 코루틴 내에서만 호출할 수 있고,
            // 아닌 곳에서 그냥 호출하려고 하면 warning 메세지가 발생한다.
            val items =
                maskList.stores.filter { item -> item.remain_stat != null && item.remain_stat != "break" }
            for (item in items) {
                item.distance = LocationDistance.distance(latitude.value!!, longitude.value!!,
                    item.lat, item.lng, "k")
            }
            val sortedItems = items.sortedBy { item -> item.distance }
            _maskList.value = sortedItems
            setLoadingData(false)
        }
    }

    fun setLoadingData(chk: Boolean) {
        _loadingData.value = chk
    }

    fun setLatitude(lat: Double) {
        _latitude.value = lat
    }

    fun setLongitude(lng: Double) {
        _longitude.value = lng
    }
}