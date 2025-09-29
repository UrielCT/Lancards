package com.example.idioms.utils

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import com.example.idioms.domain.models.Word

val WordSaver: Saver<Word, Any> = listSaver(
    save = { word ->
        listOf(
            word.id,
            word.date,
            word.word,
            word.translation,
            word.pronunciation,
            word.association,
            word.originalLang,
            word.translatedLang,
            word.keyWordA,
            word.keyWordB,
            word.category,
            word.score
        )
    },
    restore = {
        Word(
            id = it[0] as Int,
            date = it[1] as String,
            word = it[2] as String,
            translation = it[3] as String,
            pronunciation = it[4] as String,
            association = it[5] as String,
            originalLang = it[6] as String,
            translatedLang = it[7] as String,
            keyWordA = it[8] as String,
            keyWordB = it[9] as String,
            category = it[10] as String,
            score = it[11] as Int
        )
    }
)
