package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jesuspeirolopez.myflexagenda.databinding.ActivityEventCreateBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EventCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventCreateBinding

    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eventCreateBack.setOnClickListener{
            val intent = Intent(this@EventCreateActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }

        updateActualDayTextViews()

    }

    private fun updateActualDayTextViews() {

        val dayFormat = SimpleDateFormat("dd", Locale.getDefault())
        val formattedDay = dayFormat.format(calendar.time)
        binding.eventCreateDay1.text = formattedDay

        val monthFormat = SimpleDateFormat("MMMM", Locale("es", "ES"))
        val formattedMonth = monthFormat.format(calendar.time)
        binding.eventCreateDay3.text = formattedMonth

        val newYear = calendar.get(Calendar.YEAR)
        runOnUiThread {

            binding.eventCreateYear.text = newYear.toString()
        }
        val yearTextView = binding.eventCreateYear

        if (newYear != calendar.get(Calendar.YEAR)) {
            yearTextView.text = newYear.toString()
        }
    }
}