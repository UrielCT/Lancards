package com.example.idioms.domain.repository

import com.example.idioms.domain.models.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    fun getWords(): Flow<List<Word>>
    fun getWordById(id: Int): Word?
    suspend fun add(word: Word)
    suspend fun update(word: Word)
    suspend fun delete(word: Word)
}