package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import android.os.Bundle
import com.jesuspeirolopez.myflexagenda.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.year.setOnClickListener { // Iniciar la nueva actividad (YearCalendarActivity)
            val intent = Intent(this@MainActivity, YearCalendarActivity::class.java)
            startActivity(intent)
        }

    }
}