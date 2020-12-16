package org.wandukong.calendar

import java.util.*
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CalendarViewHolder(private val itemView : View) : RecyclerView.ViewHolder(itemView) {

    val tvDay = itemView.findViewById<TextView>(R.id.tv_day)

    fun onBind(
        position: Int,
        baseCalendar: BaseCalendar,
        dayList: MutableList<TextView>
    ){
        tvDay.text = baseCalendar.data[position].toString()
        tvDay.setBackgroundResource(R.drawable.circle_unselected)

        if(baseCalendar.curYear.equals(baseCalendar.calendar.get(Calendar.YEAR).toString()) &&
            baseCalendar.curMonth.equals(baseCalendar.calendar.get(Calendar.MONTH).plus(1).toString()) &&
            baseCalendar.curDay.equals(baseCalendar.data[position].toString()) ){
            tvDay.setBackgroundResource(R.drawable.circle_today)
        }

        if (position % 7 == 0){
            tvDay.setTextColor(Color.parseColor("#ff0000"))
        }

        if(position <  baseCalendar.prevMonthEndIndex ||
            position >= baseCalendar.prevMonthEndIndex + baseCalendar.curMonthMaxDay){
            tvDay.alpha = 0.3f
        }else{
            dayList.add(tvDay)
            tvDay.setOnClickListener {
                it.setBackgroundResource(R.drawable.circle_selected)

                CheckDate.preSelectedDay.value = CheckDate.curSelectedDay.value
                CheckDate.curSelectedDay.value = baseCalendar.data[position]
            }
        }
    }
}
