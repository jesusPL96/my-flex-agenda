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

        val monthName = intent.getStringExtra("monthName")
        binding.monthName.text = monthName


    }
}