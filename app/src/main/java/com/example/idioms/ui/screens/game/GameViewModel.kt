package com.example.idioms.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idioms.domain.usecases.words.GetWordsUseCase
import com.example.idioms.ui.models.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getWordsUseCase: GetWordsUseCase
):ViewModel() {
    private val allWords = getWordsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _originalLang = MutableStateFlow("Todos")
    private val _translatedLang = MutableStateFlow("Todos")
    private val _category = MutableStateFlow("Todas")
    private val _order = MutableStateFlow("Mas Nuevas")

    val filteredWords: StateFlow<List<Word>> = combine(
        allWords, _originalLang, _translatedLang, _category, _order
    ) { words, origin, dest, category, order ->

        words.filter {
            (origin == "Todos" || it.originalLang == origin) &&
                    (dest == "Todos" || it.translatedLang == dest) &&
                    (category == "Todas" || it.category == category)
        }.let { filteredList ->
            when (order) {
                "Mas Nuevas" -> filteredList.sortedByDescending { it.date }
                "Mas Antiguas" -> filteredList.sortedBy { it.date }
                "Fecha Random" -> filteredList.shuffled()
                "Alfabético" -> filteredList.sortedBy { it.word }
                "Alfabético Inverso" -> filteredList.sortedByDescending { it.word }
                else -> filteredList
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // setters
    fun setOriginLang(value: String) { _originalLang.value = value }
    fun setTranslatedLang(value: String) { _translatedLang.value = value }
    fun setCategory(value: String) { _category.value = value }
    fun setOrder(value: String) { _order.value = value }
}