package com.example.idioms.ui.screens.addword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idioms.domain.usecases.words.AddWordUseCase
import com.example.idioms.domain.usecases.words.GetWordByIdUseCase
import com.example.idioms.domain.usecases.words.UpdateWordUseCase
import com.example.idioms.ui.models.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsViewModel @Inject constructor(
    private val addWordUseCase: AddWordUseCase,
    private val getWordByIdUseCase: GetWordByIdUseCase,
    private val updateWordUseCase: UpdateWordUseCase,
):ViewModel() {

    // get word by id
    private val _selectedWordState = MutableStateFlow<WordUiState>(WordUiState.Loading)
    val selectedWordState: StateFlow<WordUiState> = _selectedWordState

    // Resetear estado a null (útil cuando creamos nueva palabra)
    fun resetSelectedWord() {
        _selectedWordState.value = WordUiState.Success(null)
    }

    // Obtener palabra por ID
    fun onGetWordById(id: Int) {
        _selectedWordState.value = WordUiState.Loading //  inicio en Loading para evitar mostrar la anterior
        viewModelScope.launch {
            getWordByIdUseCase(id)
                .catch { _selectedWordState.value = WordUiState.Error(it) }
                .collect { word ->
                    _selectedWordState.value = WordUiState.Success(word)
                }
        }
    }


    // CRUD
    fun onWordCreated(word: Word) = viewModelScope.launch { addWordUseCase(word) }
    fun onWordUpdated(word: Word) = viewModelScope.launch { updateWordUseCase(word) }

}