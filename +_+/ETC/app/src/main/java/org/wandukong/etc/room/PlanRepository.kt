package org.wandukong.etc.room

import androidx.lifecycle.LiveData


class PlanRepository(private val planDao : PlanDao) {

    val allData : LiveData<List<Plan>> = planDao.getAll()

    suspend fun addPlan(plan : Plan){
        planDao.addPlan(plan)
    }
}