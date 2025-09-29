package com.example.idioms.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idioms.domain.usecases.words.GetWordsUseCase
import com.example.idioms.domain.models.Word
import com.example.idioms.domain.usecases.words.UpdateWordUseCase
import com.example.idioms.utils.GameState
import com.example.idioms.utils.categoriesFilter
import com.example.idioms.utils.languagesFilter
import com.example.idioms.utils.orderList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val updateWordUseCase: UpdateWordUseCase
):ViewModel() {

    private val _gameUiState = MutableStateFlow<GameUiState>(GameUiState.Loading)
    val gameUiState: StateFlow<GameUiState> = _gameUiState

    private val _ui = MutableStateFlow(GameUi())
    val ui: StateFlow<GameUi> = _ui


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
                _selectedCategory,
                _selectedOriginLang,
                _selectedDestLang,
                _order
            ) { words, category, originLang, destLang, order ->

                val filtered = words.filter { word ->
                    val matchesCategory = category.isNullOrBlank() || category == categoriesFilter[0] || word.category == category
                    val matchesOrigin = originLang.isNullOrBlank() || originLang == languagesFilter[0] || word.originalLang == originLang
                    val matchesDest = destLang.isNullOrBlank() || destLang == languagesFilter[0] || word.translatedLang == destLang
                     matchesCategory && matchesOrigin && matchesDest
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

                GameUiState.Success(filtered)

            }.onStart {
                _gameUiState.value = GameUiState.Loading
            }.catch { e ->
                _gameUiState.value = GameUiState.Error(e)
            }.collect { state ->
                _gameUiState.value = state
            }
        }
    }

    fun setOriginLang(value: String?) { _selectedOriginLang.value = value }
    fun setTranslatedLang(value: String?) { _selectedDestLang.value = value }
    fun setCategory(value: String?) { _selectedCategory.value = value }
    fun setOrder(value: String) { _order.value = value }

    private fun onWordUpdated(word: Word) = viewModelScope.launch (Dispatchers.IO) {
        updateWordUseCase(word)
    }

    fun startGame(words: List<Word>) {
        val firstWord = words.firstOrNull()
        _ui.value = GameUi(
            state = GameState.RUNNING,
            isGameFinished = false,
            wordsList = words.take(_ui.value.amount.toIntOrNull() ?: words.size),
            correctWords = 0,
            index = 0,
            currentWord = firstWord
        )
    }

    fun onCheck(textColor: Int,isCorrect: Boolean) {
        _ui.update { ui ->
            ui.copy(
                isCheckEnabled = false,
                isNextEnabled = true,
                textColor = textColor,
                isGameFinished = true,
                correctWords = if (isCorrect) ui.correctWords + 1 else ui.correctWords,
                isCorrect = isCorrect
            )
        }
        val currentWord = _ui.value.currentWord
        if (isCorrect && currentWord != null) {
            val updatedWord = currentWord.copy(score = currentWord.score + 1)
            onWordUpdated(updatedWord)
        }
    }

    fun onNext(textColor: Int) {
        _ui.update { ui ->
            val nextIndex = ui.index + 1
            if (nextIndex < ui.wordsList.size) {
                ui.copy(
                    index=nextIndex,
                    word = "",
                    translation = "",
                    isCheckEnabled = true,
                    isNextEnabled = false,
                    textColor = textColor,
                    isGameFinished = false,
                    isCorrect = null,
                    currentWord = ui.wordsList[nextIndex]
                )
            }else {
                ui.copy(state = GameState.FINISHED)
            }

        }
    }

    fun onWordChange(newWord: String) { _ui.value = _ui.value.copy(word = newWord) }

    fun onTranslationChange(newTranslation: String) {
        _ui.value = _ui.value.copy(
            translation = newTranslation
        )
    }

    fun restartGame(textColor: Int) {
        _ui.value = _ui.value.copy(
            state = GameState.STOPPED,
            word = "",
            translation = "",
            isCheckEnabled = true,
            isNextEnabled = false,
            textColor = textColor,
            wordsList = emptyList(),
            correctWords = 0,
            isCorrect = null
        )
    }

    fun setAmount(amount:String){ _ui.value = _ui.value.copy(amount = amount) }

    fun setGame(game:String){ _ui.value = _ui.value.copy(game = game) }

    fun onRestart(){ _ui.value = GameUi() }

}