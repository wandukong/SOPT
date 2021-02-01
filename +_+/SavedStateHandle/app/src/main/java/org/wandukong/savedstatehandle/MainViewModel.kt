package org.wandukong.savedstatehandle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _count = MutableLiveData<Int>(0)
    val count: LiveData<Int> get() = _count

    fun plusCount(){
        _count.value = _count.value!!.plus(1)
    }

    fun minusCount(){
        _count.value = _count.value!!.minus(1)
    }
}