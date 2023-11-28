package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jesuspeirolopez.myflexagenda.databinding.ActivityEventCreateBinding

class EventCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eventCreateBack.setOnClickListener{
            val intent = Intent(this@EventCreateActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}