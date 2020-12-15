package org.wandukong.calendar

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CalendarViewHolder(private val itemView : View) : RecyclerView.ViewHolder(itemView) {

    val tvDay = itemView.findViewById<TextView>(R.id.tv_day)

    fun onBind(position : Int, baseCalendar : BaseCalendar){
        tvDay.text = baseCalendar.data[position].toString()

        if (position % 7 == 0){
            tvDay.setTextColor(Color.parseColor("#ff0000"))
        }

        if(position <  baseCalendar.prevMonthEndIndex ||
            position >= baseCalendar.prevMonthEndIndex + baseCalendar.curMonthMaxDay){
            tvDay.alpha = 0.3f
        }
    }
}
