package org.wandukong.calendar

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_plan.*
import kotlinx.android.synthetic.main.dialog_plan.view.*
import org.wandukong.calendar.calendar.CalendarAdapter
import org.wandukong.calendar.calendar.ClickData
import org.wandukong.calendar.schedule.ScheduleAdapter
import org.wandukong.calendar.util.HorizontalItemDivider
import org.wandukong.calendar.util.VerticalItemDivider

import org.wandukong.calendar.room.ScheduleViewModel
import org.wandukong.calendar.room.ScheduleData
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var calendarAdapter : CalendarAdapter
    private lateinit var scheduleAdapter : ScheduleAdapter
    private lateinit var calendarViewModel : ScheduleViewModel

    private var scheduleDate = ""
    private var inputPlan = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarViewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
        calendarAdapter = CalendarAdapter(this, this)
        scheduleAdapter = ScheduleAdapter(this, calendarViewModel, calendarAdapter)

        makeCalendar()
        makeScheduleList()
        initForScheduleList()
        changeMonth()
        selectDay()

        btn_add.setOnClickListener {
            if(ClickData.curSelectedDay.value!= 0) makeDialog()
            else Toast.makeText(this, "날짜를 선택해 주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initForScheduleList() {
        calendarAdapter.scheduleAdapter = scheduleAdapter

        rcv_schedule.apply {
            adapter = scheduleAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        calendarViewModel.monthData.observe(this, { scheduleList ->
            scheduleAdapter.data = scheduleList
            scheduleAdapter.notifyDataSetChanged()

            for(i in 0 until scheduleList.size){
                var day = scheduleList[i].date.substring(8).toInt()
                var month = scheduleList[i].date.substring(5,7).toInt()
                if( month.toString() == calendarAdapter.baseCalendar.curMonth && day.toString() == calendarAdapter.baseCalendar.curDay){
                    if(calendarAdapter.baseCalendar.dayList.size != 0)
                        calendarAdapter.baseCalendar.dayList[day-1].setBackgroundResource(R.drawable.circle_today_scheduled)
                }
                if( !(month.toString() == calendarAdapter.baseCalendar.curMonth && day.toString() == calendarAdapter.baseCalendar.curDay)){
                    if(calendarAdapter.baseCalendar.dayList.size != 0)
                        calendarAdapter.baseCalendar.dayList[day-1].setBackgroundResource(R.drawable.circle_scheduled)
                }
            }
        })
    }

    private fun makeScheduleList() {
        calendarViewModel.getMonthData(makeParam())
    }

    private fun insertDataToDatabase() {
        if(inputCheck(inputPlan)){
            val schedule = ScheduleData(inputPlan, scheduleDate)
            calendarViewModel.insertSchedule(schedule, makeParam())
            ClickData.preSelectedDay.value =ClickData.curSelectedDay.value
            ClickData.curSelectedDay.value = 0
        }
    }

    private fun makeCalendar() {

        rcv_calendar.apply {
            adapter = calendarAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 7)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            addItemDecoration(VerticalItemDivider(20))
            addItemDecoration(HorizontalItemDivider(40))
        }
    }

    private fun makeDialog() {
        val dialog  = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_plan)
        dialog.setCancelable(false)

        val calendar = calendarAdapter.baseCalendar.calendar
        scheduleDate = calendar.get(Calendar.YEAR).toString() +"-" +
                parseIntToString(calendar.get(Calendar.MONTH).plus(1)) +"-" +
                parseIntToString(ClickData.curSelectedDay.value!!.toInt())
        dialog.tv_date_dialog.text = scheduleDate

        dialog.btn_ok.setOnClickListener {
            inputPlan = dialog.et_plan_dialog.text.toString()
            insertDataToDatabase()
            dialog.dismiss()
        }
        dialog.btn_cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun selectDay() {

        ClickData.curSelectedDay.observe(this, androidx.lifecycle.Observer {
            if(ClickData.preSelectedDay.value != 0 ) {
                    calendarAdapter.baseCalendar.dayList[ClickData.preSelectedDay.value!!.minus(1)].setBackgroundResource(R.drawable.circle_unselected)
                    if(calendarAdapter.baseCalendar.curYear.equals(calendarAdapter.baseCalendar.calendar.get(Calendar.YEAR).toString()) &&
                        calendarAdapter.baseCalendar.curMonth.equals(calendarAdapter.baseCalendar.calendar.get(Calendar.MONTH).plus(1).toString()) &&
                        calendarAdapter.baseCalendar.curDay.equals(ClickData.preSelectedDay.value.toString())){
                        calendarAdapter.baseCalendar.dayList[ClickData.preSelectedDay.value!!.minus(1)].setBackgroundResource(R.drawable.circle_today)
                    }
                    if(ClickData.preSelectedDay.value == ClickData.curSelectedDay.value){
                        setCheckData()
                    }
                    for(i in 0 until scheduleAdapter.data.size){
                        val day = scheduleAdapter.data[i].date.substring(8).toInt()-1
                        if(ClickData.curSelectedDay.value == day+1){
                            calendarAdapter.baseCalendar.dayList[day].setBackgroundResource(R.drawable.circle_selected)
                        }
                        else{
                            calendarAdapter.baseCalendar.dayList[day].setBackgroundResource(R.drawable.circle_scheduled)
                        }
                    }
                }
                for(i in 0 until scheduleAdapter.data.size){
                    if(scheduleAdapter.data[i].date.substring(5,7) == calendarAdapter.baseCalendar.curMonth &&
                        scheduleAdapter.data[i].date.substring(8) == calendarAdapter.baseCalendar.curDay){
                        calendarAdapter.baseCalendar.dayList[calendarAdapter.baseCalendar.curDay.toInt()-1].setBackgroundResource(R.drawable.circle_today_scheduled)
                    }
                    if(ClickData.curSelectedDay.value!!.toInt() == calendarAdapter.baseCalendar.curDay.toInt()){
                        calendarAdapter.baseCalendar.dayList[calendarAdapter.baseCalendar.curDay.toInt()-1].setBackgroundResource(R.drawable.circle_selected)
                    }
                }
            }
        )
    }

    private fun changeMonth() {
        btn_next.setOnClickListener {
            setCheckData()
            calendarAdapter.changeToNextMonth()
            makeScheduleList()
        }
        btn_pre.setOnClickListener {
            setCheckData()
            calendarAdapter.changeToPrevMonth()
            makeScheduleList()
        }
    }

    fun refreshCurrentMonth(calendar: Calendar) {
        val year = SimpleDateFormat("yyyy", Locale.KOREAN)
        val month = SimpleDateFormat("MM", Locale.KOREAN)
        tv_year.text = year.format(calendar.time ).toString() +  "년"
        tv_month.text = month.format(calendar.time).toString() + "월"
    }

    private fun inputCheck(scheduleTitle : String): Boolean {
        return !(scheduleTitle.isNullOrEmpty())
    }

    private fun parseIntToString(intValue: Int): String {
        if(intValue < 10){
            return "0$intValue"
        }else{
            return "$intValue"
        }
    }

    private fun makeParam(): String {
        return parseIntToString(calendarAdapter.baseCalendar.calendar.get(Calendar.YEAR)) +'-'+
                parseIntToString(calendarAdapter.baseCalendar.calendar.get(Calendar.MONTH).plus(1))
    }

    private fun setCheckData(){
        ClickData.curSelectedDay.value = 0
        ClickData.preSelectedDay.value = 0
    }
}