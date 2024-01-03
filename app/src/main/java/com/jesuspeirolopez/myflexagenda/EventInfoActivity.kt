package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jesuspeirolopez.myflexagenda.databinding.ActivityEventInfoBinding
import kotlinx.coroutines.launch
import kotlin.math.log

class EventInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventInfoBinding

    private lateinit var viewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        val eventId = intent.getLongExtra("eventId", -1)
        if (eventId != -1L) {

            viewModel.viewModelScope.launch {
                val eventFromId = viewModel.getEventById(eventId)

                binding.eventInfoName.text = eventFromId.title
                binding.eventInfoDescription.text = eventFromId.description
                binding.startTimeInfo.text = eventFromId.startTime
                binding.endTimeInfo.text = eventFromId.endTime
                binding.imageView2.setImageURI(eventFromId.imagePath.toUri())
                println("hey: " + eventFromId.imagePath)
                binding.imageView2.requestLayout()
                binding.imageView2.invalidate()
            }
        } else {

        }


        binding.eventInfoBack.setOnClickListener{

            val intent = Intent(this@EventInfoActivity, MainActivity::class.java)
            startActivity(intent)

        }


    }
}