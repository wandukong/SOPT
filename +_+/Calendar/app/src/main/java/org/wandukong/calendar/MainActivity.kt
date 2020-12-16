package org.wandukong.calendar

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.Placeholder
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var calendarAdapter : CalendarAdapter
    private var inputPlan : String = ""

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

        changeMonth()
        selectDay()
        btn_add.setOnClickListener { makeDialog() }

    }

    private fun makeDialog() {
        val dialog  = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_plan, null)

        val dateDialog = dialogView.findViewById<TextView>(R.id.tv_date_dialog)
        val planDialog = dialogView.findViewById<EditText>(R.id.et_plan_dialog)

        val calendar = calendarAdapter.baseCalendar.calendar
        dateDialog.text = calendar.get(Calendar.YEAR).toString() +"-" +
                calendar.get(Calendar.MONTH).plus(1).toString() +"-" +
                CheckDate.curSelectedDay.value.toString()

        dialog.setView(dialogView)
            .setPositiveButton("ADD") { dialogInterface, i ->
                inputPlan = planDialog.text.toString()
                Log.e("MainActivity", inputPlan)
            }
            .setNegativeButton("CANCEL") { dialogInterface, i ->
            }.show()
    }

    private fun selectDay() {
        CheckDate.curSelectedDay.observe(this, androidx.lifecycle.Observer {
                if(CheckDate.preSelectedDay.value != 0 ) {
                    calendarAdapter.baseCalendar.dayList[CheckDate.preSelectedDay.value!!.minus(1)].setBackgroundResource(R.drawable.circle_unselected)
                    if(CheckDate.preSelectedDay.value == CheckDate.curSelectedDay.value){
                        calendarAdapter.baseCalendar.dayList[CheckDate.curSelectedDay.value!!.minus(1)].setBackgroundResource(R.drawable.circle_selected)
                    }
                }
            }
        )
    }

    private fun changeMonth() {
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