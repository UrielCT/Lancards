package com.example.idioms.ui.models

data class Word(
    val word: String,
    val translation: String,
    val pronunciation: String,
    val association: String,
    val keyWordA: String = "",
    val keyWordB: String = "",
    val keyWordC: String = "",
    val type: String = "",
    val id: Long = 1,

)