package com.jesuspeirolopez.myflexagenda

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jesuspeirolopez.myflexagenda.EventMO

//Métodos de acceso a la base de datos de eventos
@Dao
interface EventDAO {
    //Devuelve todos los eventos
    @Query("SELECT * FROM events")
    fun getAllEvents(): List<EventMO>

    //Devuelve el evento específicado por id
    @Query("SELECT * FROM events WHERE id = :id")
    fun getById(id: Long): EventMO

    //Inserta un evento
    @Insert
    fun insertEvent(event: EventMO)

    //Elimina un evento
    @Delete
    fun deleteEvent(event: EventMO)

    //Elimina todos los eventos
    @Query("DELETE FROM events")
    fun deleteAllEvents()

    //Elimina un evento por una id
    @Query("DELETE FROM events WHERE id = :id")
    fun deleteById(id: Long)

    //Devuelve los eventos en una fecha concreta
    @Query("SELECT * FROM events WHERE day = :day AND month = :month AND year = :year")
    fun getEventsByDate(day: Int, month: Int, year: Int): List<EventMO>

    //Devuelve los eventos ordenados para conseguir el siguiente día con un evento
    @Query("SELECT * FROM events ORDER BY year, month, day")
    fun getAllEventsSorted(): List<EventMO>

}