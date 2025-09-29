package com.example.idioms.ui.screens.addword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idioms.R
import com.example.idioms.domain.usecases.words.AddWordUseCase
import com.example.idioms.domain.usecases.words.GetWordByIdUseCase
import com.example.idioms.domain.usecases.words.UpdateWordUseCase
import com.example.idioms.domain.models.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsViewModel @Inject constructor(
    private val addWordUseCase: AddWordUseCase,
    private val getWordByIdUseCase: GetWordByIdUseCase,
    private val updateWordUseCase: UpdateWordUseCase,
):ViewModel() {

    private val _selectedWordState = MutableStateFlow<WordUiState>(WordUiState.Idle)
    val selectedWordState: StateFlow<WordUiState> = _selectedWordState.asStateFlow()

    fun resetSelectedWord() {
        _selectedWordState.value = WordUiState.Idle
    }

    fun getWordById(id: Int) {
        _selectedWordState.value = WordUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getWordByIdUseCase(id)
                if (result != null) {
                    _selectedWordState.value = WordUiState.Success(result)
                } else {
                    _selectedWordState.value = WordUiState.Error(R.string.weather_search_error)
                }
            } catch (e: Exception) {
                _selectedWordState.value = WordUiState.Error(R.string.weather_general_error)
            }
        }
    }

    fun onWordCreated(word: Word) = viewModelScope.launch (Dispatchers.IO) {
        addWordUseCase(word)
    }

    fun onWordUpdated(word: Word) = viewModelScope.launch (Dispatchers.IO) {
        updateWordUseCase(word)
    }
}