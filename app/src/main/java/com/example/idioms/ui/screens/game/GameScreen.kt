package com.example.idioms.ui.screens.game

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.idioms.R
import com.example.idioms.ui.screens.game.components.FinishedView
import com.example.idioms.ui.screens.game.components.RunningView
import com.example.idioms.ui.screens.game.components.StoppedView
import com.example.idioms.ui.theme.CommonFontSizeMiddle
import com.example.idioms.ui.theme.CommonPaddingMicro
import com.example.idioms.ui.theme.CommonPaddingMin
import com.example.idioms.utils.GameState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    navBack: () -> Unit,
    gameViewModel: GameViewModel
) {
    val ui by gameViewModel.ui.collectAsState()
    val gameUiState by gameViewModel.gameUiState.collectAsState()

    val selectedCategory by gameViewModel.selectedCategory.collectAsState()
    val selectedOrder by gameViewModel.order.collectAsState()
    val originalLanguage by gameViewModel.selectedOriginLang.collectAsState()
    val translationLanguage by gameViewModel.selectedDestLang.collectAsState()

    val defaultTextColor = MaterialTheme.colorScheme.onBackground

    LaunchedEffect(Unit) { gameViewModel.onRestart() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.words_game),
                    color = MaterialTheme.colorScheme.onBackground) },
                navigationIcon = {
                    IconButton(onClick = navBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                actions = {
                    if (ui.state == GameState.RUNNING) {
                        Text(
                            "${ui.index + 1}/${ui.wordsList.size}",
                            modifier = Modifier.padding(horizontal = CommonPaddingMin),
                            fontSize = CommonFontSizeMiddle,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface)
            )
        },

        ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(CommonPaddingMicro),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (val state = gameUiState) {
                is GameUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is GameUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.error_words_text, state.throwable))
                    }
                }
                is GameUiState.Success -> {
                    val filteredWords = state.words

                    when ( ui.state ) {
                        GameState.STOPPED -> {

                            StoppedView(
                                originalLang = originalLanguage.toString(),
                                translatedLang = translationLanguage.toString(),
                                category = selectedCategory.toString(),
                                order = selectedOrder,
                                amount = ui.amount,
                                game = ui.game,
                                amountList = (1..filteredWords.size).map { it.toString() },
                                filteredWords = filteredWords,
                                onOriginalLangChange = { gameViewModel.setOriginLang(it) },
                                onTranslatedLangChange = { gameViewModel.setTranslatedLang(it) },
                                onCategoryChange = { gameViewModel.setCategory(it) },
                                onOrderChange = { gameViewModel.setOrder(it) },
                                onAmountChange = { gameViewModel.setAmount(it) },
                                onGameChange = { gameViewModel.setGame(it)},
                                onStartClick = { gameViewModel.startGame(words = filteredWords) }
                            )
                        }

                        GameState.RUNNING -> {

                            RunningView(
                                game = ui.game,
                                index = ui.index,
                                filteredWords = ui.wordsList,
                                translation = ui.translation,
                                word = ui.word,
                                isCorrect = ui.isCorrect,
                                isCheckEnabled = ui.isCheckEnabled,
                                isNextEnabled = ui.isNextEnabled,
                                onTranslationChange = { gameViewModel.onTranslationChange(it) },
                                onWordChange = { gameViewModel.onWordChange(it) },
                                onCheck = { newIsCorrect, newTextColor ->
                                    gameViewModel.onCheck(newTextColor.value.toInt(), newIsCorrect)
                                },
                                onNext = { gameViewModel.onNext(defaultTextColor.value.toInt()) }
                            )
                        }

                        GameState.FINISHED -> {

                            FinishedView(
                                correctWords = ui.correctWords,
                                filteredWordsSize = ui.wordsList.size,
                                order = selectedOrder,
                                game = ui.game,
                                onRestart = {
                                    gameViewModel.restartGame(defaultTextColor.value.toInt())
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}