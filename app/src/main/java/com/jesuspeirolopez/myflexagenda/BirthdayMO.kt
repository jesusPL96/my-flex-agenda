package com.jesuspeirolopez.myflexagenda

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "birthdays")
data class BirthdayMO(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val month: Int,
    val day: Int
)