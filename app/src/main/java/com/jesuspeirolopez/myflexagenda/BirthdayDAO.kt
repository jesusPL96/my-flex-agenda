package com.jesuspeirolopez.myflexagenda

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

//Metodos de acceso a la base de datos de cumpleaños
@Dao
interface BirthdayDAO {
    //Devuelve todos los cumpleaños
    @Query("SELECT * FROM birthdays")
    fun getAllBirthdays(): List<BirthdayMO>

    //Inserta un cumpleaños
    @Insert
    fun insertBirthday(birthday: BirthdayMO)

    //Elimina un cumpleaños
    @Delete
    fun deleteBirthday(birthday: BirthdayMO)

    //Elimina un cumpleaños por id
    @Query("DELETE FROM birthdays WHERE id = :id")
    fun deleteBirthdayById(id: Long)

    //Devuelve todos los cumpleaños de un mes y un dia
    @Query("SELECT * FROM birthdays WHERE day = :day AND month = :month")
    fun getBirthdaysByDate(day: Int, month: Int): List<BirthdayMO>
}