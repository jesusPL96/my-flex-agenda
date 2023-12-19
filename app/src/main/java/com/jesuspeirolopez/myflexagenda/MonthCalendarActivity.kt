package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jesuspeirolopez.myflexagenda.databinding.ActivityMonthCalendarBinding
import com.jesuspeirolopez.myflexagenda.databinding.ActivityYearCalendarBinding

class MonthCalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMonthCalendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonthCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.monthCalendarBack.setOnClickListener {
            val intent = Intent(this@MonthCalendarActivity, YearCalendarActivity::class.java)
            startActivity(intent)

            finish()
        }

        val monthName = intent.getStringExtra("monthName") ?: "enero"
        val yearNumber = intent.getStringExtra("yearNumber") ?: "2023"


        binding.monthName.text = monthName
        binding.yearNumber.text = yearNumber

        val daysInMonth = getDaysInMonth(getMonthNumber(monthName))

        val daysOfMonth = (1..daysInMonth).map { it.toString() }


        val adapter = DayAdapter(this, daysOfMonth, monthName, yearNumber)
        binding.gridView.adapter = adapter


    }

    fun getDaysInMonth(month: Int): Int {
        return when (month) {
            2 -> 28
            4, 6, 9, 11 -> 30
            else -> 31
        }
    }

    fun getMonthNumber(nombreMes: String): Int {
        return when (nombreMes.lowercase()) {
            "enero" -> 1
            "febrero" -> 2
            "marzo" -> 3
            "abril" -> 4
            "mayo" -> 5
            "junio" -> 6
            "julio" -> 7
            "agosto" -> 8
            "septiembre" -> 9
            "octubre" -> 10
            "noviembre" -> 11
            "diciembre" -> 12
            else -> throw IllegalArgumentException("Nombre del mes no válido: $nombreMes")
        }
    }

}