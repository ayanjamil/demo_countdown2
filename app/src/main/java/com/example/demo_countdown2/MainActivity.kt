package com.example.demo_countdown2

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private var restTimer: CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnEventDate = findViewById<Button>(R.id.btn_eventdate)
        btnEventDate.setOnClickListener {
            setcurrenDate()
        }
    }
    private fun setcurrenDate(){
        var eventTime = findViewById<TextView>(R.id.tv_eventtime)
        eventTime.text = null
        val currentTime = findViewById<TextView>(R.id.tv_currenttime)
        val displaytimer = findViewById<TextView>(R.id.tvTimer)
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val hour = myCalendar.get(Calendar.HOUR)
        val min = myCalendar.get(Calendar.MINUTE)
        val sdf = SimpleDateFormat("mm:HH:dd:MM:yy", Locale.ENGLISH)
        var eventtime_calc: Date? = null
        val currenttime_calc = sdf.parse("$min:$hour:$day:${month + 1}:$year")
        Log.d("currentime", "$currenttime_calc")
        currentTime.text = "Current Time = $min:$hour:$day:${month + 1}:$year"
        val dpd = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            val tpd = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val selectedDate = "$selectedMinute:$selectedHour:$selectedDayOfMonth:${selectedMonth + 1}:$selectedYear"
                eventTime.text = "selectedDate: $selectedDate"
                eventtime_calc = sdf.parse(selectedDate)
                countDown(eventtime_calc,currenttime_calc,displaytimer)
            }, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true)
            tpd.show()
        }, year, month, day)
        dpd.show()
    }
    private fun countDown(eventtime_calc: Date?, currenttime_calc: Date?,displaytimer:TextView) {
        // Countdown
        if (eventtime_calc != null && currenttime_calc != null) {
            Log.d("not null", "entered and curren times are not null")

            val diff: Long = eventtime_calc!!.time - currenttime_calc.time
            Log.d("diff", "Difference between is  $diff")

            restTimer = object : CountDownTimer(diff, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val sec = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                    val min = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                    val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24
                    val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)

                    val finalop = "Days:$days \nHours :$hours\nMin :$min\nSec :$sec"
                    displaytimer.text = "$finalop"
                }

                override fun onFinish() {
                    println("Time's finished!")
                }
            }
            restTimer?.start()
        }
        // Countdown
    }



}
