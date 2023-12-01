package com.jesuspeirolopez.myflexagenda

import java.util.Date

data class Event(
    val id: Int,
    val title: String,
    val description: String,
    val imagePath: String,
    val day: Int,
    val month: Int,
    val year: Int,
    val hour: String,
    val minute: String
)
