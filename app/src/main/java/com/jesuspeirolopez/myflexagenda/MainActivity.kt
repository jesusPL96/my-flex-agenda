package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import android.os.Bundle
import com.jesuspeirolopez.myflexagenda.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.actualYear.setOnClickListener {

            val intent = Intent(this@MainActivity, YearCalendarActivity::class.java)
            startActivity(intent)
        }

        binding.addButton.setOnClickListener {

            val intent = Intent(this@MainActivity, EventCreateActivity::class.java)
            startActivity(intent)
        }

        binding.checkEvent.setOnClickListener{

            val intent = Intent(this@MainActivity, EventInfoActivity::class.java)
            startActivity(intent)

        }

        binding.dayAfter.setOnClickListener {

            calendar.add(Calendar.DAY_OF_MONTH, 1)
            updateActualDayTextViews()

        }

        binding.dayBefore.setOnClickListener {

            calendar.add(Calendar.DAY_OF_MONTH, -1)
            updateActualDayTextViews()

        }

        updateActualDayTextViews()


    }

    private fun updateActualDayTextViews() {

        val dayFormat = SimpleDateFormat("dd", Locale.getDefault())
        val formattedDay = dayFormat.format(calendar.time)
        binding.actualDay.text = formattedDay

        val monthFormat = SimpleDateFormat("MMMM", Locale("es", "ES"))
        val formattedMonth = monthFormat.format(calendar.time)
        binding.actualDay3.text = formattedMonth

        val newYear = calendar.get(Calendar.YEAR)
        runOnUiThread {

            binding.actualYear.text = newYear.toString()
        }
        val yearTextView = binding.actualYear

        if (newYear != calendar.get(Calendar.YEAR)) {
            yearTextView.text = newYear.toString()
        }
    }
}