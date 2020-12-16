package org.wandukong.calendar

import android.widget.TextView
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Calendar.*

class BaseCalendar {

    companion object {
        const val NUMBER_OF_WEEK = 7
        const val ROW_OF_CALENDAR = 6
    }
    val timestamp = LocalDateTime.now()
    val today = timestamp.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    val curYear = today.substring(0,4)
    val curMonth = today.substring(4,6)
    var curDay = today.substring(6)

    val dayList = mutableListOf<TextView>()

    val calendar = getInstance()

    var prevMonthEndIndex = 0
    var curMonthMaxDay = 0
    var remainDayForNextMonth = 0

    var data = mutableListOf<Int>()

    init {
        calendar.time = Date()
    }

    fun initBaseCalendar(refreshCallback: (Calendar) -> Unit) {
        makeMonthDay(refreshCallback)
    }

    private fun makeMonthDay(refreshCallback: (Calendar) -> Unit) {
        data.clear()
        dayList.clear()

        calendar.set(DATE, 1) // 현재 날짜를 이번 달 1일로 설정

        prevMonthEndIndex = calendar.get(DAY_OF_WEEK) - 1   // 이번달이 되기 전의 마지막 날짜의 offset (일 : 1, 토 : 7)
        curMonthMaxDay = calendar.getActualMaximum(DAY_OF_MONTH)
        remainDayForNextMonth = ROW_OF_CALENDAR * NUMBER_OF_WEEK - (prevMonthEndIndex + curMonthMaxDay) // 42칸 - 저번달 + 이번날 일 수 => 다음 달 일을 채울 수 있는 칸 수

        makePrevMonthDay(calendar.clone() as Calendar)
        makeCurMonthDay()
        makeNextMonthDay()

        refreshCallback(calendar)
    }

    private fun makePrevMonthDay(calendar: Calendar) {     // 저번달 날짜 채우기
        calendar.set(MONTH, calendar.get(MONTH) - 1)    // 이전 달로 calendar 객체 설정
        val maxDay= calendar.getActualMaximum(DATE)   // 이전 달의 최대 날짜

        for (i in maxDay-prevMonthEndIndex+1..maxDay) data.add(i)
    }

    private fun makeCurMonthDay() {      // 이번달 날짜 채우기
        for (i in 1..calendar.getActualMaximum(DATE)) data.add(i)
    }

    private fun makeNextMonthDay() {       // 다음달 날짜 채우기
        for (i in 1..remainDayForNextMonth) data.add(i)
    }


    // 저번 달 이동
    fun changeToPrevMonth(refreshCallback: (Calendar) -> Unit) {
        if(calendar.get(MONTH) == 0){
            calendar.set(YEAR, calendar.get(YEAR) - 1)
            calendar.set(MONTH, DECEMBER)
        }else {
            calendar.set(MONTH, calendar.get(MONTH) - 1)
        }
        makeMonthDay(refreshCallback)
    }


    // 다음 달 이동
    fun changeToNextMonth(refreshCallback: (Calendar) -> Unit) {
        if(calendar.get(MONTH) == DECEMBER){
            calendar.set(YEAR, calendar.get(YEAR) + 1)
            calendar.set(MONTH, JANUARY)
        }else {
            calendar.set(MONTH, calendar.get(MONTH) + 1)
        }
        makeMonthDay(refreshCallback)
    }
}