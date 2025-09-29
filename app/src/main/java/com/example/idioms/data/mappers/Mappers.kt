package com.example.idioms.data.mappers

import com.example.idioms.data.model.WordEntity
import com.example.idioms.domain.models.Word
//import com.example.idioms.ui.models.WordUiModel

fun Word.toEntity(): WordEntity {
    return WordEntity(
        id = this.id,
        date = this.date,
        word = this.word,
        translation = this.translation,
        pronunciation = this.pronunciation,
        association = this.association,
        originalLang = this.originalLang,
        translatedLang = this.translatedLang,
        keyWordA = this.keyWordA,
        keyWordB = this.keyWordB,
        category = this.category,
        score= this.score
    )
}

//fun Word.toUIModel(): WordUiModel {
//    return WordUiModel(
//        id = this.id,
//        date = this.date,
//        word = this.word,
//        translation = this.translation,
//        pronunciation = this.pronunciation,
//        association = this.association,
//        originalLang = this.originalLang,
//        translatedLang = this.translatedLang,
//        keyWordA = this.keyWordA,
//        keyWordB = this.keyWordB,
//        category = this.category,
//        score= this.score
//    )
//}

//fun WordUiModel.toWord(): Word {
//    return Word(
//        id = this.id,
//        date = this.date,
//        word = this.word,
//        translation = this.translation,
//        pronunciation = this.pronunciation,
//        association = this.association,
//        originalLang = this.originalLang,
//        translatedLang = this.translatedLang,
//        keyWordA = this.keyWordA,
//        keyWordB = this.keyWordB,
//        category = this.category,
//        score= this.score
//    )
//}



fun WordEntity.toDomain(): Word {
    return Word(
        id = this.id,
        date = this.date,
        word = this.word,
        translation = this.translation,
        pronunciation = this.pronunciation,
        association = this.association,
        originalLang = this.originalLang,
        translatedLang = this.translatedLang,
        keyWordA = this.keyWordA,
        keyWordB = this.keyWordB,
        category = this.category,
        score= this.score
    )
}