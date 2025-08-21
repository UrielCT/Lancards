package com.example.idioms.ui.screens.addword

import com.example.idioms.ui.models.Word

sealed interface WordUiState {
    object Loading: WordUiState
    data class Error(val throwable: Throwable): WordUiState
    data class Success(val word: Word?): WordUiState
    object Deleted : WordUiState
}