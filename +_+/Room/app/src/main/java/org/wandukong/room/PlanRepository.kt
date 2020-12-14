package org.wandukong.room

import androidx.lifecycle.LiveData


class PlanRepository(private val planDao : PlanDao) {

    val allData : LiveData<MutableList<Plan>> = planDao.getAll()

    suspend fun addPlan(plan : Plan){
        planDao.addPlan(plan)
    }

    suspend fun deletePlan(plan : Plan){
        planDao.deletePlan(plan)
    }
}