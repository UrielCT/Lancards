package com.example.idioms.ui.screens.home

import com.example.idioms.domain.models.Word

sealed interface WordsUiState {
    data object Loading: WordsUiState
    data class Error(val throwable: Throwable): WordsUiState
    data class Success(val words:List<Word>): WordsUiState
}