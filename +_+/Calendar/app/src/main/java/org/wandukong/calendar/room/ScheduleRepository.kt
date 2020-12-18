package org.wandukong.calendar.room

import android.util.Log


class ScheduleRepository(private val scheduleDao : ScheduleDao) {

    suspend fun getMonthSchedule(yearMonth : String): MutableList<ScheduleData> {
        return scheduleDao.getMonthSchedule(yearMonth)
    }

    suspend fun insertSchedule(plan : ScheduleData){
        scheduleDao.insertSchedule(plan)
    }

    suspend fun deleteSchedule(plan : ScheduleData){
        scheduleDao.deleteSchedule(plan)
    }
}