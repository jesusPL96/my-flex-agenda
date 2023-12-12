package com.jesuspeirolopez.myflexagenda

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jesuspeirolopez.myflexagenda.Birthday

@Dao
interface BirthdayDAO {
    @Query("SELECT * FROM birthdays")
    fun getAllBirthdays(): List<BirthdayMO>

    @Insert
    fun insertBirthday(birthday: BirthdayMO)

    @Delete
    fun deleteBirthday(birthday: BirthdayMO)

    @Query("DELETE FROM birthdays WHERE id = :id")
    fun deleteBirthdayById(id: Long)

    @Query("SELECT * FROM birthdays WHERE day = :day AND month = :month")
    fun getBirthdaysByDate(day: Int, month: Int): List<BirthdayMO>
}