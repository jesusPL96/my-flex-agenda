package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jesuspeirolopez.myflexagenda.databinding.ActivityBirthdayEventsBinding

class BirthdayEventsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBirthdayEventsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBirthdayEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.birthdaysBack.setOnClickListener {
            val intent = Intent(this@BirthdayEventsActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.addBirthdayButton.setOnClickListener {
            val intent = Intent(this@BirthdayEventsActivity, BirthdayCreate::class.java)
            startActivity(intent)
        }


    }
}