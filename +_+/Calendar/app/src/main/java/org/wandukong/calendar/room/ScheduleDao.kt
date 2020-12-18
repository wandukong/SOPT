package org.wandukong.calendar.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ScheduleDao{
    @Query("SELECT * FROM schedule_table WHERE date LIKE :yearMonth||'%' ORDER BY date")
    suspend fun getMonthSchedule(yearMonth : String): MutableList<ScheduleData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule : ScheduleData)

    @Delete
    suspend fun deleteSchedule(schedule : ScheduleData)
}
