package org.wandukong.calendar.schedule

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wandukong.calendar.R
import org.wandukong.calendar.calendar.CalendarAdapter
import org.wandukong.calendar.room.ScheduleData
import org.wandukong.calendar.room.ScheduleViewModel

class ScheduleAdapter(private val context: Context, private val calendarViewModel: ScheduleViewModel, private val calendarAdapter: CalendarAdapter) : RecyclerView.Adapter<ScheduleViewHolder>() {
    var data = mutableListOf<ScheduleData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.onBind(data[position], calendarViewModel, calendarAdapter)
    }

    override fun getItemCount(): Int = data.size
}