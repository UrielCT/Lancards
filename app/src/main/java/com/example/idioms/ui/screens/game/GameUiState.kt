package com.example.idioms.ui.screens.game

import com.example.idioms.domain.models.Word

sealed interface GameUiState {
    data object Loading: GameUiState
    data class Error(val throwable: Throwable): GameUiState
    data class Success(val words:List<Word>): GameUiState
}