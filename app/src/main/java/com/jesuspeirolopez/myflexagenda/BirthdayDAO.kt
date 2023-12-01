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
}