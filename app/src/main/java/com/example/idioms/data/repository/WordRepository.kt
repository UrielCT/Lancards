package com.example.idioms.data.repository

import com.example.idioms.data.datastore.local.dao.WordDao
import com.example.idioms.ui.models.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordRepository @Inject constructor( private val wordDao: WordDao) {

    // get all Words
    val words: Flow<List<Word>> = wordDao.getWords()
        .map { items ->
            items.map {
                Word(
                    id = it.id,
                    date = it.date,
                    word = it.word,
                    translation = it.translation,
                    pronunciation = it.pronunciation,
                    association = it.association,
                    originalLang = it.originalLang,
                    translatedLang = it.translatedLang,
                    keyWordA = it.keyWordA,
                    keyWordB = it.keyWordB,
                    keyWordC = it.keyWordC,
                    category = it.category
                )
            }
        }



    // get word by Id
    fun getWordById(id: Int): Flow<Word?> {
        return wordDao.getWordById(id).map {
            it?.let { word ->
                Word(
                    id = word.id,
                    date = word.date,
                    word = word.word,
                    translation = word.translation,
                    pronunciation = word.pronunciation,
                    association = word.association,
                    originalLang = word.originalLang,
                    translatedLang = word.translatedLang,
                    keyWordA = word.keyWordA,
                    keyWordB = word.keyWordB,
                    keyWordC = word.keyWordC,
                    category = word.category
                )
            }
        }
    }

    // get filtered words


    // add new Word
    suspend fun add(word: Word){ wordDao.addWord(word.toEntity()) }

    // update Word
    suspend fun update(word: Word){ wordDao.updateWord(word.toEntity()) }

    // delete Word
    suspend fun delete(word: Word){ wordDao.deleteWord(word.toEntity()) }

}