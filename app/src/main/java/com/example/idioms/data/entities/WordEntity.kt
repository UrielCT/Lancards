package com.example.idioms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.idioms.ui.models.Word

@Entity
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
    val keyWordC: String,
    val category: String,
){
    fun toModel(): Word {
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
            keyWordC = this.keyWordC,
            category = this.category,

        )
    }
}
