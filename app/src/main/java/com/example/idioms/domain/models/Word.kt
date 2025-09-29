package com.example.idioms.domain.models

data class Word(
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
){
    companion object {
        fun empty(): Word = Word(
            id = 0,
            date = System.currentTimeMillis().toString(),
            word = "",
            translation = "",
            pronunciation = "",
            association = "",
            //originalLang = "Español",   // o tu valor por defecto
            originalLang = "",   // o tu valor por defecto
            //translatedLang = "Inglés", // o el que quieras por defecto
            translatedLang = "", // o el que quieras por defecto
            keyWordA = "",
            keyWordB = "",
            category = "",
            //category = "General",
            score = 0
        )
    }
}
