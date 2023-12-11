package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jesuspeirolopez.myflexagenda.databinding.ActivityBirthdayCreateBinding

class BirthdayCreate : AppCompatActivity() {

    private lateinit var binding: ActivityBirthdayCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBirthdayCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backCreateBirthday.setOnClickListener {
            val intent = Intent(this@BirthdayCreate, BirthdayEventsActivity::class.java)
            startActivity(intent)
        }

    }
}