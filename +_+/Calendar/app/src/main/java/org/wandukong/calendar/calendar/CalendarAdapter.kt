package org.wandukong.calendar.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wandukong.calendar.MainActivity
import org.wandukong.calendar.R
import org.wandukong.calendar.room.ScheduleData
import org.wandukong.calendar.schedule.ScheduleAdapter
import java.util.*

class CalendarAdapter(private val context : Context, private val mainActivity: MainActivity) : RecyclerView.Adapter<CalendarViewHolder>() {

    val baseCalendar = BaseCalendar()
    lateinit var scheduleAdapter: ScheduleAdapter

    init {
        baseCalendar.initBaseCalendar {
            refreshView(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_day, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.onBind(position, baseCalendar, baseCalendar.dayList)
    }

    override fun getItemCount() = 42

    fun changeToPrevMonth() {
        baseCalendar.changeToPrevMonth {
            refreshView(it)
        }
    }

    fun changeToNextMonth() {
        baseCalendar.changeToNextMonth {
            refreshView(it)
        }
    }

    private fun refreshView(calendar: Calendar) {
        notifyDataSetChanged()
        mainActivity.refreshCurrentMonth(calendar)
    }
}