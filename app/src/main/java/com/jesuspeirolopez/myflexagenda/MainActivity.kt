package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import android.os.Bundle
import com.jesuspeirolopez.myflexagenda.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //calendar para cambiar dias
    private val calendar: Calendar = Calendar.getInstance()
    //event adapter para el recycler view
    private lateinit var eventAdapter: EventAdapter

    private lateinit var agendaDatabase: AgendaDatabase

    private lateinit var viewModel: EventViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        agendaDatabase = Room.databaseBuilder(
            applicationContext,
            AgendaDatabase::class.java, "agenda-database"
        ).build()

        binding.actualYear.setOnClickListener {

            val intent = Intent(this@MainActivity, YearCalendarActivity::class.java)
            startActivity(intent)
        }

        binding.addButton.setOnClickListener {

            val intent = Intent(this@MainActivity, EventCreateActivity::class.java)
            startActivity(intent)
        }

        binding.checkEvent.setOnClickListener{

            val intent = Intent(this@MainActivity, EventInfoActivity::class.java)
            startActivity(intent)

        }

        binding.dayAfter.setOnClickListener {

            calendar.add(Calendar.DAY_OF_MONTH, 1)
            updateActualDayTextViews()

        }

        binding.dayBefore.setOnClickListener {

            calendar.add(Calendar.DAY_OF_MONTH, -1)
            updateActualDayTextViews()

        }

        binding.dayWithEventAfter.setOnClickListener {

            deleteAllEvents()

        }

        updateActualDayTextViews()

        viewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        //Les añado ejemplos con exampleEvents

        eventAdapter = EventAdapter(viewModel.getEventsByCurrentDate(binding.actualDay.text.toString().toInt(),
            getMonthNumber(binding.actualDay3.text.toString()),
            binding.actualYear.text.toString().toInt()))
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.adapter = eventAdapter


    }

    private fun updateActualDayTextViews() {

        val dayFormat = SimpleDateFormat("dd", Locale.getDefault())
        val formattedDay = dayFormat.format(calendar.time)
        binding.actualDay.text = formattedDay

        val monthFormat = SimpleDateFormat("MMMM", Locale("es", "ES"))
        val formattedMonth = monthFormat.format(calendar.time)
        binding.actualDay3.text = formattedMonth

        val newYear = calendar.get(Calendar.YEAR)
        runOnUiThread {

            binding.actualYear.text = newYear.toString()
        }
        val yearTextView = binding.actualYear

        if (newYear != calendar.get(Calendar.YEAR)) {
            yearTextView.text = newYear.toString()
        }
    }

    //Para eliminar, es de prueba
    private fun exampleEvents(): List<Event> {

        return listOf(
            Event(1,"Sacar al perro", "Descripción", "", 1, 4,2023, "00", "00"),
            Event(2, "Cita dentista", "Descripción", "", 1,2,2023, "23", "59"),

        )
    }

    private fun getEventsByCurrentDate(): List<EventMO> {

        var eventsList: List<EventMO> = emptyList()

        CoroutineScope(Dispatchers.IO).launch {
            eventsList = agendaDatabase.eventDao().getEventsByDate(
                binding.actualDay.text.toString().toInt(),
                getMonthNumber(binding.actualDay3.text.toString()),
                binding.actualYear.text.toString().toInt()
            )
        }

        return eventsList

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

    fun convertEventMOListToEventList(eventMOList: List<EventMO>): List<Event> {
        return eventMOList.map { eventMO ->
            Event(
                id = eventMO.id.toInt(), // Convertir Long a Int si es necesario
                title = eventMO.title,
                description = eventMO.description,
                imagePath = eventMO.imagePath,
                day = eventMO.day,
                month = eventMO.month,
                year = eventMO.year,
                startTime = eventMO.startTime,
                endTime = eventMO.endTime
            )
        }
    }

    fun printEventMOList(eventMOList: List<EventMO>) {
        for (eventMO in eventMOList) {
            println("EventMO ID: ${eventMO.id}")
            println("Title: ${eventMO.title}")
        }
    }

    fun deleteAllEvents() {

        CoroutineScope(Dispatchers.IO).launch {
            agendaDatabase.eventDao().deleteAllEvents()
        }

    }


}