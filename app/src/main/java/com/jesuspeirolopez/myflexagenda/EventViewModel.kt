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

    private val eventsLiveData: MutableLiveData<List<EventMO>> = MutableLiveData()

    fun getEventsByCurrentDate(day: Int, month: Int, year: Int): LiveData<List<EventMO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val events = agendaDatabase.eventDao().getEventsByDate(day, month, year)
            withContext(Dispatchers.Main) {
                eventsLiveData.value = events
            }
        }
        return eventsLiveData
    }

    fun deleteEventById(eventId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            agendaDatabase.eventDao().deleteById(eventId)
        }
    }
}