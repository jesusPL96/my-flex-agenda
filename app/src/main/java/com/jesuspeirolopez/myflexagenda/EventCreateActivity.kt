package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.jesuspeirolopez.myflexagenda.databinding.ActivityEventCreateBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EventCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventCreateBinding

    private val calendar: Calendar = Calendar.getInstance()

    private lateinit var agendaDatabase: AgendaDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        agendaDatabase = Room.databaseBuilder(
            applicationContext,
            AgendaDatabase::class.java, "agenda-database"
        ).build()



        binding.eventCreateBack.setOnClickListener{
            val intent = Intent(this@EventCreateActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }

        binding.addEventButton.setOnClickListener {
            val title = binding.eventTitleInsert.text.toString()
            val description = binding.eventDescriptionInsert.text.toString()
            val startTime = binding.eventCreateTextTime1.text.toString()
            val endTime = binding.eventCreateTextTime2.text.toString()
            val day = binding.eventCreateDay1.text.toString().toInt()
            val month = getMonthNumber(binding.eventCreateDay3.text.toString())
            val year = binding.eventCreateYear.text.toString().toInt()

            val event = EventMO(title = title, description = description, startTime = startTime, endTime = endTime, day = day, month = month, year = year, imagePath = "" )

            if(startTime==""||endTime==""||title==""){
                Toast.makeText(this@EventCreateActivity, "Error: No dejes campos vacíos", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    agendaDatabase.eventDao().insertEvent(event)
                }


                Toast.makeText(this@EventCreateActivity, "Evento guardado", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@EventCreateActivity, MainActivity::class.java)
                startActivity(intent)

                finish()
            }


        }

        binding.eventCreateDay1.text = intent.getStringExtra("day")
        binding.eventCreateDay3.text = intent.getStringExtra("month")
        binding.eventCreateYear.text = intent.getStringExtra("year")


    }

    fun getMonthNumber(nombreMes: String): Int {
        return when (nombreMes.lowercase()) {
            "enero" -> 1
            "febrero" -> 2
            "marzo" -> 3
            "abril" -> 4
            "mayo" -> 5
            "junio" -> 6
            "julio" -> 7
            "agosto" -> 8
            "septiembre" -> 9
            "octubre" -> 10
            "noviembre" -> 11
            "diciembre" -> 12
            else -> throw IllegalArgumentException("Nombre del mes no válido: $nombreMes")
        }
    }

    fun toDate(dia: Int, mes: Int, ano: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, ano)
        calendar.set(Calendar.MONTH, mes - 1)
        calendar.set(Calendar.DAY_OF_MONTH, dia)
        return calendar.time
    }

}