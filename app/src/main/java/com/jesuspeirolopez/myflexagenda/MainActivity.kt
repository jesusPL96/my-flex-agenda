package com.jesuspeirolopez.myflexagenda

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.jesuspeirolopez.myflexagenda.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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

    //la database
    private lateinit var agendaDatabase: AgendaDatabase

    //el event view Model
    private lateinit var viewModel: EventViewModel

    //el birthday view model
    private lateinit var viewBirthdayModel: BirthdayViewModel

    private var eventObserver: Observer<List<EventMO>>? = null


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

            val dayNumber = binding.actualDay.text.toString()
            val monthName = binding.actualDay3.text.toString()
            val yearNumber = binding.actualYear.text.toString()

            val intent = Intent(this@MainActivity, EventCreateActivity::class.java)
            intent.putExtra("day", dayNumber)
            intent.putExtra("month", monthName)
            intent.putExtra("year", yearNumber)
            startActivity(intent)
        }

        binding.birthdayCheck.setOnClickListener {
            val intent = Intent(this@MainActivity, BirthdayEventsActivity::class.java)
            startActivity(intent)
        }

        binding.dayAfter.setOnClickListener {

            calendar.add(Calendar.DAY_OF_MONTH, 1)
            updateActualDayTextViews()

            //Events
            loadEventsForCurrentDate()

            //Birthdays
            viewBirthdayModel = ViewModelProvider(this).get(BirthdayViewModel::class.java)

            viewBirthdayModel.getBirthdaysByDate(
                binding.actualDay.text.toString().toInt(),
                getMonthNumber(binding.actualDay3.text.toString())
            ).observe(this) { birthdayList ->

                var birthdayString = ""

                for (i in birthdayList.indices) {
                    birthdayString += birthdayList[i].name
                    if (i != birthdayList.size - 1) {
                        birthdayString += ", "
                    }
                }

                Log.d("Birthdays: ", birthdayString)
                if (birthdayString == "") {
                    birthdayString = "¡No hay cumpleaños!"
                }
                binding.birthdayName.text = birthdayString
            }


        }

        binding.dayBefore.setOnClickListener {

            calendar.add(Calendar.DAY_OF_MONTH, -1)
            updateActualDayTextViews()

            loadEventsForCurrentDate()

            //Birthday
            viewBirthdayModel = ViewModelProvider(this).get(BirthdayViewModel::class.java)

            viewBirthdayModel.getBirthdaysByDate(
                binding.actualDay.text.toString().toInt(),
                getMonthNumber(binding.actualDay3.text.toString())
            ).observe(this) { birthdayList ->

                var birthdayString = ""

                for (i in birthdayList.indices) {
                    birthdayString += birthdayList[i].name
                    if (i != birthdayList.size - 1) {
                        birthdayString += ", "
                    }
                }

                Log.d("Birthdays: ", birthdayString)
                if (birthdayString == "") {
                    birthdayString = "¡No hay cumpleaños!"
                }
                binding.birthdayName.text = birthdayString
            }

        }

        binding.dayWithEventAfter.setOnClickListener {

            val dayNumber = binding.actualDay.text.toString().toInt()
            val monthNumber = getMonthNumber(binding.actualDay3.text.toString())
            val yearNumber = binding.actualYear.text.toString().toInt()

            //Con o sin coroutine el bug sigue, se bloquea

            viewModel.getSortedEvents().observe(this@MainActivity) { allEvents ->
                val eventSorted =
                    findClosestEvent(allEvents, dayNumber, monthNumber, yearNumber, true)

                if (eventSorted != null) {
                    val formattedDay = String.format("%02d", eventSorted.day)
                    binding.actualDay.text = formattedDay
                    binding.actualDay3.text = getMonthName(eventSorted.month)
                    binding.actualYear.text = eventSorted.year.toString()

                    calendar.set(Calendar.YEAR, eventSorted.year)
                    calendar.set(Calendar.MONTH, eventSorted.month - 1)
                    calendar.set(Calendar.DAY_OF_MONTH, eventSorted.day)

                    loadEventsForCurrentDate()

                    //Birthdays
                    viewBirthdayModel = ViewModelProvider(this).get(BirthdayViewModel::class.java)

                    viewBirthdayModel.getBirthdaysByDate(
                        binding.actualDay.text.toString().toInt(),
                        getMonthNumber(binding.actualDay3.text.toString())
                    ).observe(this) { birthdayList ->

                        var birthdayString = ""

                        for (i in birthdayList.indices) {
                            birthdayString += birthdayList[i].name
                            if (i != birthdayList.size - 1) {
                                birthdayString += ", "
                            }
                        }

                        Log.d("Birthdays: ", birthdayString)
                        if (birthdayString == "") {
                            birthdayString = "¡No hay cumpleaños!"
                        }
                        binding.birthdayName.text = birthdayString
                    }
                }
            }


        }

        binding.dayWithEventBefore.setOnClickListener {

            val dayNumber = binding.actualDay.text.toString().toInt()
            val monthNumber = getMonthNumber(binding.actualDay3.text.toString())
            val yearNumber = binding.actualYear.text.toString().toInt()


            viewModel.getSortedEvents().observe(this@MainActivity) { allEvents ->
                val eventSorted =
                    findClosestEvent(allEvents, dayNumber, monthNumber, yearNumber, false)

                if (eventSorted != null) {
                    val formattedDay = String.format("%02d", eventSorted.day)
                    binding.actualDay.text = formattedDay
                    binding.actualDay3.text = getMonthName(eventSorted.month)
                    binding.actualYear.text = eventSorted.year.toString()

                    calendar.set(Calendar.YEAR, eventSorted.year)
                    calendar.set(Calendar.MONTH, eventSorted.month - 1)
                    calendar.set(Calendar.DAY_OF_MONTH, eventSorted.day)

                    loadEventsForCurrentDate()

                    //Birthdays
                    viewBirthdayModel = ViewModelProvider(this).get(BirthdayViewModel::class.java)

                    viewBirthdayModel.getBirthdaysByDate(
                        binding.actualDay.text.toString().toInt(),
                        getMonthNumber(binding.actualDay3.text.toString())
                    ).observe(this) { birthdayList ->

                        var birthdayString = ""

                        for (i in birthdayList.indices) {
                            birthdayString += birthdayList[i].name
                            if (i != birthdayList.size - 1) {
                                birthdayString += ", "
                            }
                        }

                        Log.d("Birthdays: ", birthdayString)
                        if (birthdayString == "") {
                            birthdayString = "¡No hay cumpleaños!"
                        }
                        binding.birthdayName.text = birthdayString
                    }
                }
            }


        }

        //Compruebo si viene un intent con extra o no para los valores del día
        if (intent.hasExtra("day")) {

            //Vienen como String?, es decir, no como String
            val yearString: String? = intent.getStringExtra("year")
            val monthString: String? = intent.getStringExtra("month")
            val dayString: String? = intent.getStringExtra("day")

            //Los arreglo para que sean enteros y string normales comprobando el nulo
            val year: Int = yearString?.toIntOrNull() ?: 0
            val month: String = monthString ?: "enero"
            val day: Int = dayString?.toIntOrNull() ?: 0

            //Pinto los días correctos
            binding.actualYear.text = yearString
            binding.actualDay3.text = monthString
            binding.actualDay.text = dayString

            //Pongo los valores correctos en calendar
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, getMonthNumber(month) - 1)
            calendar.set(Calendar.DAY_OF_MONTH, day)

        } else {
            updateActualDayTextViews()
        }

        //Events
        viewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        eventAdapter = EventAdapter(
            viewModel.getEventsByCurrentDate(
                binding.actualDay.text.toString().toInt(),
                getMonthNumber(binding.actualDay3.text.toString()),
                binding.actualYear.text.toString().toInt()
            ), viewModel
        )
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.adapter = eventAdapter

        //Birthday
        viewBirthdayModel = ViewModelProvider(this).get(BirthdayViewModel::class.java)

        viewBirthdayModel.getBirthdaysByDate(
            binding.actualDay.text.toString().toInt(),
            getMonthNumber(binding.actualDay3.text.toString())
        ).observe(this) { birthdayList ->

            var birthdayString = ""

            for (i in birthdayList.indices) {
                birthdayString += birthdayList[i].name
                if (i != birthdayList.size - 1) {
                    birthdayString += ", "
                }
            }

            Log.d("Birthdays: ", birthdayString)
            if (birthdayString == "") {
                birthdayString = "¡No hay cumpleaños!"
            }
            binding.birthdayName.text = birthdayString
        }


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
            Event(1, "Sacar al perro", "Descripción", "", 1, 4, 2023, "00", "00"),
            Event(2, "Cita dentista", "Descripción", "", 1, 2, 2023, "23", "59"),

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

    fun getMonthName(monthNumber: Int): String {
        return when (monthNumber) {
            1 -> "enero"
            2 -> "febrero"
            3 -> "marzo"
            4 -> "abril"
            5 -> "mayo"
            6 -> "junio"
            7 -> "julio"
            8 -> "agosto"
            9 -> "septiembre"
            10 -> "octubre"
            11 -> "noviembre"
            12 -> "diciembre"
            else -> throw IllegalArgumentException("Número de mes no válido: $monthNumber")
        }
    }

    fun deleteAllEvents() {

        CoroutineScope(Dispatchers.IO).launch {
            agendaDatabase.eventDao().deleteAllEvents()
        }

    }

    fun findClosestEvent(
        allEvents: List<EventMO>,
        actualDay1: Int,
        actualDay3: Int,
        actualYear: Int,
        after: Boolean
    ): EventMO? {

        val currentCalendar = Calendar.getInstance().apply {
            set(actualYear, actualDay3, actualDay1)
        }

        val filteredEvents = if (after) {
            //Filtra eventos que ocurren después del día proporcionado
            allEvents.filter { event ->
                val eventDate = Calendar.getInstance().apply {
                    set(event.year, event.month, event.day)
                }
                return@filter eventDate.timeInMillis > currentCalendar.timeInMillis
            }
        } else {
            //Filtrar eventos que ocurren antes del día proporcionado
            allEvents.filter { event ->
                val eventDate = Calendar.getInstance().apply {
                    set(event.year, event.month, event.day)
                }
                return@filter eventDate.timeInMillis < currentCalendar.timeInMillis
            }
        }

        // Encontrar el evento más cercano entre los eventos filtrados
        return filteredEvents.minByOrNull { event ->
            val eventDate = Calendar.getInstance().apply {
                set(event.year, event.month, event.day)
            }
            return@minByOrNull Math.abs(currentCalendar.timeInMillis - eventDate.timeInMillis)
        }
    }

    private fun loadEventsForCurrentDate() {
        val currentDay = binding.actualDay.text.toString().toInt()
        val currentMonth = getMonthNumber(binding.actualDay3.text.toString())
        val currentYear = binding.actualYear.text.toString().toInt()

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.loadEventsForDate(currentDay, currentMonth, currentYear)
        }
    }

    //No funciona igual
    private fun loadBirthdaysForCurrentDate() {

        val currentDay = binding.actualDay.text.toString().toInt()
        val currentMonth = getMonthNumber(binding.actualDay3.text.toString())

        CoroutineScope(Dispatchers.Main).launch {
            viewBirthdayModel.loadBirthdaysForDate(currentDay, currentMonth)
        }

    }


}