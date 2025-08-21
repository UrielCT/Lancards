package com.example.idioms.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idioms.domain.usecases.words.DeleteWordUseCase
import com.example.idioms.domain.usecases.words.GetWordsUseCase
import com.example.idioms.ui.models.Word
import com.example.idioms.ui.screens.addword.WordUiState
import com.example.idioms.ui.screens.home.WordsUiState.Error
import com.example.idioms.ui.screens.home.WordsUiState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val deleteWordUseCase: DeleteWordUseCase
):ViewModel() {

    // get all words
//    val wordsUiState: StateFlow<WordsUiState> = getWordsUseCase().map( WordsUiState::Success)
//        .catch { Error(it) }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)
//

    // Palabras crudas desde Room
    private val allWords: StateFlow<List<Word>> =
        getWordsUseCase()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtros
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedCategory = MutableStateFlow<String?>(null)
    private val _selectedOriginLang = MutableStateFlow<String?>(null)
    private val _selectedDestLang = MutableStateFlow<String?>(null)

    // Palabras filtradas combinando estados
    val filteredWords: StateFlow<List<Word>> = combine(
        allWords, _searchQuery, _selectedCategory, _selectedOriginLang, _selectedDestLang
    ) { words, query, category, originLang, destLang ->

        words.filter { word ->
            val matchesQuery = query.isBlank() || word.word.contains(query, ignoreCase = true)

            val matchesCategory =
                category.isNullOrBlank() || category == "Todas" || word.category == category

            val matchesOrigin =
                originLang.isNullOrBlank() || originLang == "Todos" || word.originalLang == originLang

            val matchesDest =
                destLang.isNullOrBlank() || destLang == "Todos"  || word.translatedLang == destLang

            matchesQuery && matchesCategory && matchesOrigin && matchesDest
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    // Actions
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelected(category: String?) {
        _selectedCategory.value = category
    }

    fun onOriginLangSelected(lang: String?) {
        _selectedOriginLang.value = lang
    }

    fun onDestLangSelected(lang: String?) {
        _selectedDestLang.value = lang
    }

    fun onWordRemove(word: Word) = viewModelScope.launch { deleteWordUseCase(word) }

}