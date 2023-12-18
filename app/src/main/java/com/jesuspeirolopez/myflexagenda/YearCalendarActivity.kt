package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jesuspeirolopez.myflexagenda.databinding.ActivityYearCalendarBinding

class YearCalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYearCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYearCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val monthButtons = arrayOf(
            binding.januaryButton, binding.februaryButton, binding.marchButton,
            binding.aprilButton, binding.mayButton, binding.juneButton,
            binding.julyButton, binding.augustButton, binding.septemberButton,
            binding.octoberButton, binding.novemberButton, binding.decemberButton
        )


        binding.yearCalendarBack.setOnClickListener {

            val intent = Intent(this@YearCalendarActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }

        binding.yearAfter.setOnClickListener {
            var yearNumber = binding.yearCalendarNumber.text.toString().toInt()
            yearNumber++
            binding.yearCalendarNumber.text = yearNumber.toString()
        }

        binding.yearBefore.setOnClickListener {
            var yearNumber = binding.yearCalendarNumber.text.toString().toInt()
            yearNumber--
            binding.yearCalendarNumber.text = yearNumber.toString()
        }

        for ((index, button) in monthButtons.withIndex()) {
            button.setOnClickListener {
                val monthName = getMonthName(index + 1)
                val intent = Intent(this, MonthCalendarActivity::class.java)
                intent.putExtra("monthName", monthName)
                startActivity(intent)
            }
        }



    }

    private fun getMonthName(monthNumber: Int): String {
        val monthNames = arrayOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
        return monthNames[monthNumber - 1]
    }

}