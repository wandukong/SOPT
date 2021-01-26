package org.wandukong.maskinfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wandukong.maskinfo.MaskServiceImpl.customEnqueue
import retrofit2.Call
import java.util.*
import java.util.stream.Collectors

class MainViewModel : ViewModel(){
    var maskList = MutableLiveData<List<ResponseStoreData.Store>>()
    private val service: Call<ResponseStoreData> = MaskServiceImpl.service.getMaskInfo(37.188078, 127.043002)

    init {
        fetchMaskInfo()
    }

    fun fetchMaskInfo(){
        service.customEnqueue(
            onSuccess = {
                val items = it.stores
                    .stream()
                    .filter { item -> item.remain_stat != null && item.remain_stat != "break"}
                    .collect(Collectors.toList())
                maskList.postValue(items as List<ResponseStoreData.Store>)
            },
            onFail = {
              maskList.postValue(Collections.emptyList())
            },
            onError = {
            }
        )
    }
}