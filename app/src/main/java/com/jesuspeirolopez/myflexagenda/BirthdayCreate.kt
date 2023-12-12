package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jesuspeirolopez.myflexagenda.databinding.ActivityBirthdayCreateBinding

class BirthdayCreate : AppCompatActivity() {

    private lateinit var binding: ActivityBirthdayCreateBinding
    private lateinit var viewModel: BirthdayViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBirthdayCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(BirthdayViewModel::class.java)


        binding.createBirthdayButton.setOnClickListener {

            val name = binding.birthdayCreateName.text.toString()
            val day = binding.dayAndMonthPicker.dayOfMonth
            val month = binding.dayAndMonthPicker.month+1

            val birthdayToInsert = BirthdayMO(name = name, day = day, month = month)

            viewModel.insertBirthday(birthdayToInsert)

            Toast.makeText(this@BirthdayCreate, "Cumpleaños guardado", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@BirthdayCreate, BirthdayEventsActivity::class.java)
            startActivity(intent)

            finish()

        }


        binding.backCreateBirthday.setOnClickListener {
            val intent = Intent(this@BirthdayCreate, BirthdayEventsActivity::class.java)
            startActivity(intent)
        }

    }
}