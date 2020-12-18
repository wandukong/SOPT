package org.wandukong.calendar.calendar

import androidx.lifecycle.MutableLiveData

class ClickData {
    companion object{
        var preSelectedDay =  MutableLiveData<Int>().apply { postValue(0) }
        var curSelectedDay =  MutableLiveData<Int>().apply { postValue(0) }
    }
}