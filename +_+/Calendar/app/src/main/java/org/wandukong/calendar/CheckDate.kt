package org.wandukong.calendar

import android.util.MutableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.*

class CheckDate {
    companion object{
        var preSelectedDay =  MutableLiveData<Int>().apply { postValue(0) }
        var curSelectedDay =  MutableLiveData<Int>().apply { postValue(0) }
    }
}