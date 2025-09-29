@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.idioms.ui.screens.addword

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.idioms.R
import com.example.idioms.ui.components.ProgressFullScreen
import com.example.idioms.domain.models.Word
import com.example.idioms.ui.components.WordForm
import com.example.idioms.ui.theme.CommonFontSizeLarge
import com.example.idioms.ui.theme.CommonPaddingDefault
import com.example.idioms.ui.theme.CommonPaddingMin
import com.example.idioms.utils.WordSaver

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWordScreen(
    navBack: () -> Unit,
    wordsViewModel: WordsViewModel = hiltViewModel(),
    wordId: Int? = null,
    showSnackBar: (String) -> Unit
) {
    val wordUiState by wordsViewModel.selectedWordState.collectAsStateWithLifecycle()
    val keyboardVisible = isKeyboardVisible()
    val focusManager = LocalFocusManager.current

    var editableWord by rememberSaveable(stateSaver = WordSaver) { mutableStateOf(Word.empty()) }
    val updatedWordTxt = stringResource(R.string.word_updated)
    val addedWordTxt = stringResource(R.string.word_added)

    LaunchedEffect(wordUiState, wordId) {
        when (wordUiState) {
            is WordUiState.Success -> {
                editableWord = (wordUiState as WordUiState.Success).word ?: Word.empty()
            }
            is WordUiState.Idle -> editableWord = Word.empty()
            else -> {}
        }
        if (wordId != null) {
            wordsViewModel.getWordById(wordId)
        } else {
            wordsViewModel.resetSelectedWord()
        }
    }

    fun saveWord() {
        focusManager.clearFocus()
        if (wordId == null) {
            wordsViewModel.onWordCreated(editableWord)
            showSnackBar(addedWordTxt)
        } else {
            wordsViewModel.onWordUpdated(editableWord)
            showSnackBar(updatedWordTxt)
        }
        wordsViewModel.resetSelectedWord()
        navBack()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (wordId == null) stringResource(R.string.add_word)
                        else stringResource(R.string.edit_word),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                actions = {
                    if (keyboardVisible) {
                        FilledIconButton(
                            onClick = { saveWord() },
                            shape = CircleShape,
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        bottomBar = {
            if (!keyboardVisible) {
                val state = wordUiState
                if (state is WordUiState.Success || state is WordUiState.Idle) {
                    Button(
                        onClick = { saveWord() },
                        modifier = Modifier
                            .navigationBarsPadding()
                            .fillMaxWidth()
                            .padding(vertical = CommonPaddingMin, horizontal = CommonPaddingDefault),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )

                    ) {
                        Text(stringResource(R.string.btn_save), fontSize = CommonFontSizeLarge)
                    }
                }
            }
        }
    ) { paddingValues ->

        when (wordUiState) {
            is WordUiState.Idle -> {
                WordForm(
                    paddingValues = paddingValues,
                    word = editableWord,
                    onWordChange = { editableWord = it }
                )
            }

            is WordUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    ProgressFullScreen()
                }
            }

            is WordUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.error_word),
                        color = MaterialTheme.colorScheme.error)
                }
            }

            is WordUiState.Success -> {
                WordForm(
                    paddingValues = paddingValues,
                    word = editableWord,
                    onWordChange = {
                        editableWord = it
                    }
                )
            }
        }

    }
}

@Composable
fun isKeyboardVisible(): Boolean {
    val ime = WindowInsets.ime
    val density = LocalDensity.current
    val imeBottom = ime.getBottom(density)
    return imeBottom > 0
}