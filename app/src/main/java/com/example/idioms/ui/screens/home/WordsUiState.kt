package com.example.idioms.ui.screens.home

import com.example.idioms.ui.models.Word

sealed interface WordsUiState {
    object Loading: WordsUiState
    data class Error(val throwable: Throwable): WordsUiState
    data class Success(val words:List<Word>): WordsUiState
}