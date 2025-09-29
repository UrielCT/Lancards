package com.example.idioms.data.repository

import com.example.idioms.data.datastore.local.dao.WordDao
import com.example.idioms.data.mappers.toDomain
import com.example.idioms.data.mappers.toEntity
import com.example.idioms.domain.models.Word
import com.example.idioms.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordRepositoryImpl @Inject constructor(
    private val wordDao: WordDao
): WordRepository {


    override fun getWords(): Flow<List<Word>> {
        return wordDao.getWords().map { items -> items.map { it.toDomain() } }
    }


    override fun getWordById(id: Int): Word? {
        return wordDao.getWordById(id)?.toDomain()
    }


    // add new Word
    override suspend fun add(word: Word){ wordDao.addWord(word.toEntity()) }

    // update Word
    override suspend fun update(word: Word){ wordDao.updateWord(word.toEntity()) }

    // delete Word
    override suspend fun delete(word: Word){ wordDao.deleteWord(word.toEntity()) }

}