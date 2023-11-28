package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jesuspeirolopez.myflexagenda.databinding.ActivityEventInfoBinding

class EventInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eventInfoBack.setOnClickListener{

            val intent = Intent(this@EventInfoActivity, MainActivity::class.java)
            startActivity(intent)

        }


    }
}