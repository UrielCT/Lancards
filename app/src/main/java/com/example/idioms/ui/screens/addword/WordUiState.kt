package com.example.idioms.ui.screens.addword

import com.example.idioms.domain.models.Word

sealed interface WordUiState {
    data object Idle : WordUiState
    data object Loading : WordUiState
    data class Success(val word: Word?) : WordUiState
    data class Error(val msgRes: Int? = null) : WordUiState
}
