package org.wandukong.etc.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlanDao{
    @Query("SELECT * FROM plan_table")
    fun getAll(): LiveData<MutableList<Plan>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlan(plan : Plan)

    @Delete
    suspend fun deletePlan(plan : Plan)
}
