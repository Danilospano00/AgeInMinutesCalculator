package com.example.dobcalc

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate: TextView? = null
    private var selectedDateInMinutesTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnDatePicker: Button = findViewById(R.id.datePickerButton)
        tvSelectedDate = findViewById<TextView>(R.id.selectedDate)
        selectedDateInMinutesTextView = findViewById<TextView>(R.id.selectedDateInMinutes)
        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }


    private fun clickDatePicker() {

        var selectedDate: String? = null
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, dayOfMonth ->
                Toast.makeText(
                    this,
                    "Year was $selectedYear, selectedMonth was ${selectedMonth + 1}, day was $dayOfMonth",
                    Toast.LENGTH_LONG
                ).show()
                selectedDate = "$dayOfMonth/${selectedMonth + 1}/$selectedYear"
                tvSelectedDate?.text = selectedDate

                val simpleDateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                } else {
                    TODO("VERSION.SDK_INT < N")
                }
                val theDate = simpleDateFormat.parse(selectedDate)
                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000

                    val currentDate =
                        simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000
                        selectedDateInMinutesTextView?.text =
                            "${selectedDateInMinutes - currentDateInMinutes}"
                    }

                }

            }, year, month, day
        )

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}