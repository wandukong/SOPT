package org.wandukong.calendar.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat

@Entity(tableName = "schedule_table")
data class ScheduleData(
    val title: String,
    val date : String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
