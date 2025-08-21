package com.example.idioms.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.idioms.data.dao.WordDao
import com.example.idioms.data.entities.WordEntity

@Database(entities = [WordEntity::class], version = 1)
abstract class IdiomsDatabase: RoomDatabase() {

    abstract fun wordDao(): WordDao

}