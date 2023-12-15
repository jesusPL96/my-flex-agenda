package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jesuspeirolopez.myflexagenda.databinding.ActivityBirthdayEventsBinding

class BirthdayEventsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBirthdayEventsBinding
    private lateinit var viewModel: BirthdayViewModel
    private lateinit var birthdayAdapter: BirthdayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBirthdayEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(BirthdayViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView2)
        birthdayAdapter = BirthdayAdapter(viewModel.getAllBirthdays(), viewModel)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.adapter = birthdayAdapter


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