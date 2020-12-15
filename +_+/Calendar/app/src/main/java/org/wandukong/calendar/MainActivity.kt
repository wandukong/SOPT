package org.wandukong.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var calendarAdapter : CalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarAdapter = CalendarAdapter(this, this)

        rcv_calendar.apply {
            adapter = calendarAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 7)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            addItemDecoration(VerticalItemDivider(20))
            addItemDecoration(HorizontalItemDivider(40))
        }

        btn_next.setOnClickListener {
            calendarAdapter.changeToNextMonth()
        }
        btn_pre.setOnClickListener {
            calendarAdapter.changeToPrevMonth()
        }
    }

    fun refreshCurrentMonth(calendar: Calendar) {
        val year = SimpleDateFormat("yyyy", Locale.KOREAN)
        val month = SimpleDateFormat("MM", Locale.KOREAN)
        tv_year.text = year.format(calendar.time ).toString() +  "년"
        tv_month.text = month.format(calendar.time).toString() + "월"
    }
}