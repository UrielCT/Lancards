package com.example.idioms.ui.models

import java.util.Date

data class Word(
    val word: String,
    val translation: String,
    val pronunciation: String,
    val association: String,
    val originalLang: String,
    val translatedLang: String,
    val keyWordA: String = "",
    val keyWordB: String = "",
    val keyWordC: String = "",
    val category: String = "",
    val date: Date ,
    val id: Long = 1,
)
