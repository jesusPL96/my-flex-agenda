package com.jesuspeirolopez.myflexagenda

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [EventMO::class, BirthdayMO::class], version = 1)
abstract class AgendaDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDAO
    abstract fun birthdayDao(): BirthdayDAO
}