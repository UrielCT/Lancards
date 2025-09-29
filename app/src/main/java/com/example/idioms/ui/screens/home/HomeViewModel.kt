package com.example.idioms.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idioms.domain.usecases.words.DeleteWordUseCase
import com.example.idioms.domain.usecases.words.GetWordsUseCase
import com.example.idioms.domain.models.Word
import com.example.idioms.utils.categoriesFilter
import com.example.idioms.utils.languagesFilter
import com.example.idioms.utils.orderList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val deleteWordUseCase: DeleteWordUseCase
):ViewModel() {

    private val _wordsUiState = MutableStateFlow<WordsUiState>(WordsUiState.Loading)
    val wordsUiState: StateFlow<WordsUiState> = _wordsUiState

    // Filtros
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedCategory = MutableStateFlow<String?>(categoriesFilter[0])
    val selectedCategory: StateFlow<String?> = _selectedCategory

    private val _selectedOriginLang = MutableStateFlow<String?>(languagesFilter[0])
    val selectedOriginLang: StateFlow<String?> = _selectedOriginLang

    private val _selectedDestLang = MutableStateFlow<String?>(languagesFilter[0])
    val selectedDestLang: StateFlow<String?> = _selectedDestLang

    private val _order = MutableStateFlow(orderList[0])
    val order: StateFlow<String> = _order

    init {
        observeWords()
    }

    private fun observeWords() {
        viewModelScope.launch {
            combine(
                getWordsUseCase(),
                _searchQuery,
                _selectedCategory,
                _selectedOriginLang,
                _selectedDestLang,
                _order
            ) { array: Array<Any?> ->

                val words = array[0] as List<Word>
                val query = array[1] as String
                val category = array[2] as String?
                val originLang = array[3] as String?
                val destLang = array[4] as String?
                val order = array[5] as String

                val filtered = words.filter { word ->
                    val matchesQuery = query.isBlank() || word.word.contains(query, ignoreCase = true)
                    val matchesCategory = category.isNullOrBlank() || category == categoriesFilter[0] || word.category == category
                    val matchesOrigin = originLang.isNullOrBlank() || originLang == languagesFilter[0] || word.originalLang == originLang
                    val matchesDest = destLang.isNullOrBlank() || destLang == languagesFilter[0] || word.translatedLang == destLang
                    matchesQuery && matchesCategory && matchesOrigin && matchesDest
                }.let { filteredList ->
                    when (order) {
                        orderList[0] -> filteredList.sortedByDescending { it.date }
                        orderList[1] -> filteredList.sortedBy { it.date }
                        orderList[2] -> filteredList.shuffled()
                        orderList[3] -> filteredList.sortedBy { it.word }
                        orderList[4] -> filteredList.sortedByDescending { it.word }
                        orderList[5] -> filteredList.sortedByDescending { it.score }
                        orderList[6] -> filteredList.sortedBy { it.score }
                        else -> filteredList
                    }
                }

                WordsUiState.Success(filtered)

            }.catch { e ->
                WordsUiState.Error(e)
            }.collect {
                _wordsUiState.value = it
            }
        }
    }

    // Actions
    fun onSearchQueryChanged(query: String) { _searchQuery.value = query }

    fun onCategorySelected(category: String?) { _selectedCategory.value = category }

    fun onOrderSelected(order: String) { _order.value = order }

    fun onOriginLangSelected(lang: String?) { _selectedOriginLang.value = lang }

    fun onDestLangSelected(lang: String?) { _selectedDestLang.value = lang }


    fun onWordRemove(word: Word) = viewModelScope.launch (Dispatchers.IO) {
        deleteWordUseCase(word)
    }

}