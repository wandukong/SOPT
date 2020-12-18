package org.wandukong.calendar.schedule

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.wandukong.calendar.R
import org.wandukong.calendar.calendar.CalendarAdapter
import org.wandukong.calendar.calendar.ClickData
import org.wandukong.calendar.room.ScheduleData
import org.wandukong.calendar.room.ScheduleViewModel

class ScheduleViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    val tvDate = itemView.findViewById<TextView>(R.id.tv_date_schedule)
    val tvTitle = itemView.findViewById<TextView>(R.id.tv_title_schedule)
    val btnDelete = itemView.findViewById<ImageButton>(R.id.btn_delete_schedule)

    fun onBind(data: ScheduleData, scheduleViewModel: ScheduleViewModel, calendarAdapter: CalendarAdapter){
        tvDate.text = data.date
        tvTitle.text = data.title

        btnDelete.setOnClickListener {
            scheduleViewModel.deleteSchedule(data, data.date.substring(0,7))
            if(calendarAdapter.baseCalendar.curDay == data.date.substring(8) && calendarAdapter.baseCalendar.curMonth == data.date.substring(5,7)){
                calendarAdapter.baseCalendar.dayList[data.date.substring(8).toInt()-1].setBackgroundResource(R.drawable.circle_today)
            }
            else{
                calendarAdapter.baseCalendar.dayList[data.date.substring(8).toInt()-1].setBackgroundResource(R.drawable.circle_unselected)
            }
        }
    }
}