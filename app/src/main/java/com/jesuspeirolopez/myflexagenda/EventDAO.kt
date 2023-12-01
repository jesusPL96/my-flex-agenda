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

    @Insert
    fun insertEvent(event: EventMO)

    @Delete
    fun deleteEvent(event: EventMO)
}