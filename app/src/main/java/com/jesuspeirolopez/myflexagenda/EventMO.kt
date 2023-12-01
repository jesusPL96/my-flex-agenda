package com.jesuspeirolopez.myflexagenda

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "events")
data class EventMO(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val imagePath: String,
    val day: Int,
    val month: Int,
    val year: Int,
    val startTime: String,
    val endTime: String
)