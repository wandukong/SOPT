package org.wandukong.etc.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat

@Entity(tableName = "plan_table")
data class Plan(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name="title")
    val title: String,
    @ColumnInfo(name="date")
    val date : String = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis())
)
