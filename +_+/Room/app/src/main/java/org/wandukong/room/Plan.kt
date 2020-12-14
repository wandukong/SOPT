package org.wandukong.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat

@Entity(tableName = "plan_table")
data class Plan(
    val title: String,
    val date : String = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis())
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
