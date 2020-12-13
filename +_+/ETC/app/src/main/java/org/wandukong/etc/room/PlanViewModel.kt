package org.wandukong.etc.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlanViewModel(application: Application) : AndroidViewModel(application) {

    val allData : LiveData<List<Plan>>
    private val repository : PlanRepository

    init {
        val planDao = PlanDatabase.getDatabase(application).planDao()
        repository = PlanRepository(planDao)
        allData = repository.allData
    }

    fun addPlan(plan : Plan){
        viewModelScope.launch(Dispatchers.IO){
            repository.addPlan(plan)
        }
    }
}