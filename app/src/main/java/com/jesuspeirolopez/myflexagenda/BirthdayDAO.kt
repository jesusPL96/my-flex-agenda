package com.jesuspeirolopez.myflexagenda

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jesuspeirolopez.myflexagenda.Birthday

@Dao
interface BirthdayDAO {
    @Query("SELECT * FROM birthdays")
    fun getAllBirthdays(): List<Birthday>

    @Insert
    fun insertBirthday(birthday: Birthday)

    @Delete
    fun deleteBirthday(birthday: Birthday)
}