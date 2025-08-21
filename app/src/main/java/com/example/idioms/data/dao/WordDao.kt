package com.example.idioms.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.idioms.data.entities.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * from WordEntity")
    fun getWords(): Flow<List<WordEntity>>

    @Query("SELECT * FROM WordEntity WHERE id = :id LIMIT 1")
    fun getWordById(id: Int): Flow<WordEntity?>

    @Insert
    suspend fun addWord(item: WordEntity)

    @Update
    suspend fun updateWord(item: WordEntity)

    @Delete
    suspend fun deleteWord(item: WordEntity)

}