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

            //Datos en las variables a guardar
            val name = binding.birthdayCreateName.text.toString()
            val day = binding.dayAndMonthPicker.dayOfMonth
            val month = binding.dayAndMonthPicker.month+1

            //Se crea el cumpleaños a guardar
            val birthdayToInsert = BirthdayMO(name = name, day = day, month = month)

            viewModel.insertBirthday(birthdayToInsert)

            //Aviso de que se ha guardado correctamente
            Toast.makeText(this@BirthdayCreate, "Cumpleaños guardado", Toast.LENGTH_SHORT).show()

            //Paso a la actividad de la lista de cumpleaños
            val intent = Intent(this@BirthdayCreate, BirthdayEventsActivity::class.java)
            startActivity(intent)

            finish()

        }

        //Boton para volver
        binding.backCreateBirthday.setOnClickListener {
            val intent = Intent(this@BirthdayCreate, BirthdayEventsActivity::class.java)
            startActivity(intent)
        }

    }
}