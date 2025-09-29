package com.example.idioms.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.idioms.utils.Constants

@Entity(tableName = Constants.E_WORD)
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val word: String,
    val translation: String,
    val pronunciation: String,
    val association: String,
    val originalLang: String,
    val translatedLang: String,
    val keyWordA: String,
    val keyWordB: String,
    val category: String,
    val score: Int
)
