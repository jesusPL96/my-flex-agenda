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


        binding.yearCalendarBack.setOnClickListener {

            val intent = Intent(this@YearCalendarActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}