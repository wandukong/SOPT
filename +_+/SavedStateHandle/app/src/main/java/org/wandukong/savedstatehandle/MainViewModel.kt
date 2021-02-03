package org.wandukong.savedstatehandle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class MainViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private var savedCount = savedStateHandle.get<Int>("count") ?: 0
        set(value) {
            savedStateHandle.set("count", value)
            field = value
            _count.value = savedCount
        }
    private val _count = MutableLiveData<Int>(savedCount)
    val count : LiveData<Int> get() = _count

    fun plusCount(){
        savedCount++
    }

    fun minusCount(){
        savedCount--
    }
}