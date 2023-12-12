package com.jesuspeirolopez.myflexagenda

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope

import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BirthdayViewModel(application: Application) : AndroidViewModel(application) {


    private val agendaDatabase: AgendaDatabase =
        Room.databaseBuilder(application, AgendaDatabase::class.java, "agenda-database")
            .build()

    private val birthdaysLiveData: MutableLiveData<List<BirthdayMO>> = MutableLiveData()


    fun getBirthdaysByDate(day: Int, month: Int): LiveData<List<BirthdayMO>> {
        return liveData(Dispatchers.IO) {
            emit(agendaDatabase.birthdayDao().getBirthdaysByDate(day, month))
        }
    }

    fun insertBirthday(birthday: BirthdayMO) {
        viewModelScope.launch(Dispatchers.IO) {
            agendaDatabase.birthdayDao().insertBirthday(birthday)
        }
    }

    fun getAllBirthdays(): LiveData<List<BirthdayMO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val birthdays = agendaDatabase.birthdayDao().getAllBirthdays()
            withContext(Dispatchers.Main) {
                birthdaysLiveData.value = birthdays
            }
        }
        return birthdaysLiveData
    }


}