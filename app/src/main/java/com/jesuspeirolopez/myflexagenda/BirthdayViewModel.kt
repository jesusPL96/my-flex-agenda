package com.jesuspeirolopez.myflexagenda

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData

import androidx.room.Room
import kotlinx.coroutines.Dispatchers

class BirthdayViewModel(application: Application) : AndroidViewModel(application) {


    private val agendaDatabase: AgendaDatabase =
        Room.databaseBuilder(application, AgendaDatabase::class.java, "agenda-database")
            .build()


    fun getBirthdaysByDate(day: Int, month: Int): LiveData<List<BirthdayMO>> {
        return liveData(Dispatchers.IO) {
            emit(agendaDatabase.birthdayDao().getBirthdaysByDate(day, month))
        }
    }

}