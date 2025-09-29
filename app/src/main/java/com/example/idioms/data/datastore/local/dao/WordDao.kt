package com.example.idioms.data.datastore.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.idioms.data.model.WordEntity
import com.example.idioms.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * from ${Constants.E_WORD}")
    fun getWords(): Flow<List<WordEntity>>

    @Query("SELECT * FROM ${Constants.E_WORD} WHERE ${Constants.P_WORD_ID} = :id LIMIT 1")
    fun getWordById(id: Int): WordEntity?

    @Insert
    suspend fun addWord(item: WordEntity)

    @Update
    suspend fun updateWord(item: WordEntity)

    @Delete
    suspend fun deleteWord(item: WordEntity)

}