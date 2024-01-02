package com.jesuspeirolopez.myflexagenda

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jesuspeirolopez.myflexagenda.EventMO

@Dao
interface EventDAO {
    @Query("SELECT * FROM events")
    fun getAllEvents(): List<EventMO>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getById(id: Long): EventMO

    @Insert
    fun insertEvent(event: EventMO)

    @Delete
    fun deleteEvent(event: EventMO)

    @Query("DELETE FROM events")
    fun deleteAllEvents()

    @Query("DELETE FROM events WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT * FROM events WHERE day = :day AND month = :month AND year = :year")
    fun getEventsByDate(day: Int, month: Int, year: Int): List<EventMO>

    @Query("SELECT * FROM events ORDER BY year, month, day")
    fun getAllEventsSorted(): List<EventMO>

}