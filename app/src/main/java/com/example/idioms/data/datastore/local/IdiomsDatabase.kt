package com.example.idioms.data.datastore.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.idioms.data.datastore.local.dao.WordDao
import com.example.idioms.data.model.WordEntity
import com.example.idioms.utils.Constants

@Database(entities = [WordEntity::class], version = Constants.DB_INIT_VERSION)
abstract class IdiomsDatabase: RoomDatabase() {

    abstract fun wordDao(): WordDao

}