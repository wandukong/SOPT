package org.wandukong.calendar.room

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {

    var monthData : MutableLiveData<MutableList<ScheduleData>> = MutableLiveData()
    private val repository : ScheduleRepository

    init {
        val scheduleDao = ScheduleDatabase.getDatabase(application).scheduleDaoo()
        repository = ScheduleRepository(scheduleDao)
    }

    fun getMonthData(yearMonth: String){
        viewModelScope.launch(Dispatchers.IO){
            monthData.postValue(repository.getMonthSchedule(yearMonth))
        }
    }

    fun insertSchedule(plan : ScheduleData, yearMonth: String){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertSchedule(plan)
            monthData.postValue(repository.getMonthSchedule(yearMonth))
        }
    }

    fun deleteSchedule(plan : ScheduleData, yearMonth: String){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteSchedule(plan)
            monthData.postValue(repository.getMonthSchedule(yearMonth))
        }
    }
}