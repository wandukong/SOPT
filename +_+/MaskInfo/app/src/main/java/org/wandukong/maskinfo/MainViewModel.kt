package org.wandukong.maskinfo

import android.text.BoringLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wandukong.maskinfo.MaskServiceImpl.customEnqueue
import java.util.*

class MainViewModel : ViewModel(){
    private val _maskList = MutableLiveData<List<ResponseStoreData.Store>>()
    val maskList: LiveData<List<ResponseStoreData.Store>> get() = _maskList

    private val _latitude = MutableLiveData<Double>()
    val latitude: LiveData<Double> get() = _latitude

    private val _longitude = MutableLiveData<Double>()
    val longitude: LiveData<Double> get() = _longitude

    private val _loadingData = MutableLiveData<Boolean>(false)
    val loadingData: LiveData<Boolean> get() = _loadingData

    private val service = MaskServiceImpl.service

    fun fetchMaskInfo(){
        setLoadingData()
        service.getMaskInfo(latitude.value!!, longitude.value!!)
            .customEnqueue(
            onSuccess = {
                val items = it.stores.filter { item -> item.remain_stat != null && item.remain_stat != "break" }
                for(item in items){
                    item.distance = LocationDistance.distance(latitude.value!!, longitude.value!!,
                        item.lat, item.lng, "k")
                }
                val sortedItems = items.sortedBy { item -> item.distance }
                _maskList.postValue(sortedItems)
                _loadingData.postValue(false)
            },
            onFail = {
                _maskList.postValue(Collections.emptyList())
                _loadingData.postValue(false)
            },
            onError = {}
        )
    }

    fun setLoadingData(){
        _loadingData.value = true
    }
    fun setLatitude(lat : Double){
        _latitude.value = lat
    }
    fun setLongitude(lng : Double){
        _longitude.value = lng
    }
}