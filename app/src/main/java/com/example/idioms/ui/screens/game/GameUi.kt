package com.example.idioms.ui.screens.game

import android.graphics.Color
import com.example.idioms.domain.models.Word
import com.example.idioms.utils.GameState
import com.example.idioms.utils.gameList

data class GameUi(
    val state: GameState = GameState.STOPPED,
    val word: String = "",
    val translation: String = "",
    val isCheckEnabled: Boolean = true,
    val isNextEnabled: Boolean = false,
    val textColor: Int = Color.BLACK,
    val isGameFinished: Boolean = false,
    val wordsList: List<Word> = emptyList(),
    val correctWords: Int = 0,
    val isCorrect: Boolean? = null,
    val currentWord: Word? = null,
    val index: Int = 0,
    val amount:String = "",
    val game:String = gameList[0]
)
