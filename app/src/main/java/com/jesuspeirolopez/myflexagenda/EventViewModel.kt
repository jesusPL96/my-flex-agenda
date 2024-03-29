package com.jesuspeirolopez.myflexagenda

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventViewModel(application: Application) : AndroidViewModel(application) {

    private val agendaDatabase: AgendaDatabase =
        Room.databaseBuilder(application, AgendaDatabase::class.java, "agenda-database")
            .build()

    //Se tiene que usar una LiveData para el RecyclerView y actualizar correctamente los datos en ejecución
    private val eventsLiveData: MutableLiveData<List<EventMO>> = MutableLiveData()

    //Consigue los eventos de la fecha actual
    fun getEventsByCurrentDate(day: Int, month: Int, year: Int): LiveData<List<EventMO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val events = agendaDatabase.eventDao().getEventsByDate(day, month, year)
            withContext(Dispatchers.Main) {
                eventsLiveData.value = events
            }
        }
        return eventsLiveData
    }

    //Elimina el evento por una id
    fun deleteEventById(eventId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            agendaDatabase.eventDao().deleteById(eventId)
        }
    }

    //Devuelve el evento por una id
    suspend fun getEventById(eventId: Long): EventMO {
        return withContext(Dispatchers.IO) {
            agendaDatabase.eventDao().getById(eventId)
        }
    }
    //Devuelve los eventos ordenados
    fun getSortedEvents(): LiveData<List<EventMO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val events = agendaDatabase.eventDao().getAllEventsSorted()
            withContext(Dispatchers.Main) {
                eventsLiveData.value = events
            }
        }
        return eventsLiveData
    }

    fun loadEventsForDate(day: Int, month: Int, year: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            val events = agendaDatabase.eventDao().getEventsByDate(day, month, year)
            withContext(Dispatchers.Main) {
                eventsLiveData.value = events
            }
        }
    }

}