package org.wandukong.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var allData : LiveData<MutableList<Plan>>
    private val repository : PlanRepository
    var newPlan : String? = null

    init {
        val planDao = PlanDatabase.getDatabase(application).planDao()
        repository = PlanRepository(planDao)
        allData = getAll()
    }

    fun getAll() : LiveData<MutableList<Plan>>{
        return repository.getAll()
    }

    fun addPlan(plan : String){
        if(plan.isNullOrBlank())
            return
        viewModelScope.launch(Dispatchers.IO){
            repository.addPlan(Plan(plan))
        }
    }

    fun deletePlan(plan : Plan){
        viewModelScope.launch(Dispatchers.IO){
            repository.deletePlan(plan)
        }
    }
}