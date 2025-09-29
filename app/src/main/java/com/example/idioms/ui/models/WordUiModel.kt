package com.example.idioms.ui.models

data class WordUiModel(
    val id:Int = 0,
    val date: String = "",
    val word: String = "",
    val translation: String = "",
    val pronunciation: String = "",
    val association: String = "",
    val originalLang: String = "",
    val translatedLang: String = "",
    val keyWordA: String = "",
    val keyWordB: String = "",
    val category: String = "",
    val score:Int = 0,
)