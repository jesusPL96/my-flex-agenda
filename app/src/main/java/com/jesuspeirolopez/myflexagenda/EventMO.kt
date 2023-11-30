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
    val eventDate: Date,
    val hour: Int,
    val minute: Int
)