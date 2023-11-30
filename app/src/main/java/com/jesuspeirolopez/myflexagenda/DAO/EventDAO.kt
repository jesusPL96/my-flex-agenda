package com.jesuspeirolopez.myflexagenda.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jesuspeirolopez.myflexagenda.Event

@Dao
interface EventDAO {
    @Query("SELECT * FROM events")
    fun getAllEvents(): List<Event>

    @Insert
    fun insertEvent(event: Event)

    @Delete
    fun deleteEvent(event: Event)
}