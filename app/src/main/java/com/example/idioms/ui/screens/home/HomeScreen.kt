package com.example.idioms.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.School
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.example.idioms.R
import com.example.idioms.ui.components.EditDeleteDialog
import com.example.idioms.ui.components.FiltersBottomSheet
import com.example.idioms.ui.components.TutorialDialog
import com.example.idioms.domain.models.Word
import com.example.idioms.ui.theme.CommonFontSizeDefault
import com.example.idioms.ui.theme.CommonFontSizeLarge
import com.example.idioms.ui.theme.CommonFontSizeMiddle
import com.example.idioms.ui.theme.CommonFontSizeXLarge
import com.example.idioms.ui.theme.CommonFontSizeXXLarge
import com.example.idioms.ui.theme.CommonPaddingDefault
import com.example.idioms.ui.theme.CommonPaddingLarge
import com.example.idioms.ui.theme.CommonPaddingMicro
import com.example.idioms.ui.theme.CommonPaddingMin
import com.example.idioms.ui.theme.HomeBottomAppBarHeight

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navToGame: () -> Unit,
    navToEditWord: (Int) -> Unit,
    navToAddWord: () -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    showSnackBar: (String) -> Unit
) {
    val wordsUiState by homeViewModel.wordsUiState.collectAsState()
    val searchQuery by homeViewModel.searchQuery.collectAsState()

    var showFilters by remember { mutableStateOf(false) }
    val selectedCategory by homeViewModel.selectedCategory.collectAsState()
    val selectedOrder by homeViewModel.order.collectAsState()
    val originalLanguage by homeViewModel.selectedOriginLang.collectAsState()
    val translationLanguage by homeViewModel.selectedDestLang.collectAsState()

    var input by remember { mutableStateOf("") }
    var hasFocus by remember { mutableStateOf(false) }
    var selectedWord by remember { mutableStateOf<Word?>(null) }
    var showTutorial by remember { mutableStateOf(false) }
    val deletedWordTxt = stringResource(R.string.word_deleted)

    val focusManager = LocalFocusManager.current
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    var pendingOpenSheet by remember { mutableStateOf(false) }

    LaunchedEffect(imeVisible, pendingOpenSheet) {
        if (!imeVisible) {
            focusManager.clearFocus()
            if (pendingOpenSheet) {
                showFilters = true
                pendingOpenSheet = false
            }
        }
    }

    Scaffold(
        topBar = {
            HomeTopAppBar(
                isDarkTheme = isDarkTheme,
                searchQuery = searchQuery,
                focused = hasFocus,
                input = input,
                filteredWords = if (wordsUiState is WordsUiState.Success) {
                    (wordsUiState as WordsUiState.Success).words
                } else emptyList(),
                clearInput = { input = "" },
                onToggleTheme = { onToggleTheme() },
                hasFocus = { hasFocus = it },
                onValueChange = { homeViewModel.onSearchQueryChanged(it) },
                onFocusManage = { focusManager.clearFocus() },
                onFilterClick = {
                    if (imeVisible) {
                        focusManager.clearFocus()
                        pendingOpenSheet = true
                    } else {
                        showFilters = true
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { navToAddWord() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.fab_add_word)
                )
            }
        },


        bottomBar = {
            HomeBottomAppBar(
                onShowTutorial = { showTutorial = true },
                navToGame = { navToGame() }
            )
        }

    ) { paddingValues ->

        when (wordsUiState) {
            is WordsUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is WordsUiState.Error -> {
                val message = (wordsUiState as WordsUiState.Error)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.error_words_text,message))
                }
            }

            is WordsUiState.Success -> {
                val filteredWords = (wordsUiState as WordsUiState.Success).words

                if (filteredWords.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.unfound_words),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        items(filteredWords, key = { it.id }) { word ->
                            Spacer(modifier = Modifier.height(CommonPaddingMin))
                            WordCard(word = word, onClick = { selectedWord = word })
                            Spacer(modifier = Modifier.height(CommonPaddingMin))
                        }
                    }
                }
            }
        }

        selectedWord?.let { word ->
            EditDeleteDialog(
                onDismissRequest = { selectedWord = null },
                word = word,
                onEditClick = {
                    selectedWord?.let { word -> navToEditWord(word.id) }
                    selectedWord = null
                },
                onDeleteClick = {
                    showSnackBar(deletedWordTxt)
                    selectedWord?.let { homeViewModel.onWordRemove(word) }
                    selectedWord = null
                }
            )
        }

        if (showTutorial) {
            TutorialDialog(onExit = { showTutorial = false })
        }

        if (showFilters) {
            FiltersBottomSheet(
                onDismissRequest = { showFilters = false },
                selectedCategory = selectedCategory.toString(),
                selectedOrder = selectedOrder,
                originalLanguage = originalLanguage.toString(),
                translationLanguage = translationLanguage.toString(),
                onCategorySelected = { homeViewModel.onCategorySelected(it) },
                onOriginalLangSelected = { homeViewModel.onOriginLangSelected(it) },
                onTranslLangSelected = { homeViewModel.onDestLangSelected(it) },
                onOrderSelected = { homeViewModel.onOrderSelected(it) }
            )
        }

    }
}


@Composable
fun HomeBottomAppBar(
    onShowTutorial: () -> Unit,
    navToGame: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = CommonPaddingMicro,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(HomeBottomAppBarHeight)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = CommonPaddingDefault),
            horizontalArrangement = Arrangement
                .spacedBy(CommonPaddingLarge),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onShowTutorial() }) {
                Icon(
                    Icons.Default.School,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            Button(
                onClick = { navToGame() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.btn_play_game),
                    fontSize = CommonFontSizeLarge
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    isDarkTheme: Boolean,
    searchQuery: String,
    focused: Boolean,
    input: String,
    filteredWords: List<Word>,
    clearInput: () -> Unit,
    onToggleTheme: () -> Unit,
    hasFocus: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    onFocusManage: () -> Unit,
    onFilterClick: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            IconButton(
                onClick = { onToggleTheme() }
            ) {
                Icon(
                    imageVector = if (isDarkTheme)
                        Icons.Default.LightMode
                    else
                        Icons.Default.NightsStay,
                    contentDescription = null
                )
            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged { focusState -> hasFocus(focusState.isFocused) },
                    value = searchQuery,
                    onValueChange = { onValueChange(it) },
                    placeholder = {
                        Text(
                            stringResource(R.string.txt_search),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    leadingIcon = {
                        IconButton(onClick = { onFocusManage() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(R.string.txt_search),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    trailingIcon = {
                        if (focused || input.isNotEmpty()) {
                            IconButton(onClick = {
                                clearInput()
                                onFocusManage()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = CommonFontSizeDefault,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(CommonPaddingMin),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        cursorColor = MaterialTheme.colorScheme.primary
                    )

                )
                Spacer(modifier = Modifier.width(CommonPaddingMin))
                Text(
                    text = "${filteredWords.size}",
                    fontSize = CommonFontSizeDefault,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        actions = {
            IconButton(onClick = { onFilterClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}


@Composable
private fun WordCard(
    onClick: () -> Unit,
    word: Word
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = CommonPaddingDefault)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = CommonPaddingMicro)
    ) {
        Column(
            modifier = Modifier
                .padding(CommonPaddingDefault)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = word.word,
                fontSize = CommonFontSizeXXLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(CommonPaddingMin))

            Text(
                text = word.translation,
                fontSize = CommonFontSizeXLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(CommonPaddingMin))

            Text(
                text = "( ${word.pronunciation} )",
                fontSize = CommonFontSizeLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(CommonPaddingMin))

            val associationText = remember(word.association, word.keyWordA, word.keyWordB) {
                buildAnnotatedString {
                    val association = word.association
                    val keywordA = word.keyWordA
                    val keywordB = word.keyWordB
                    var i = 0

                    while (i < association.length) {
                        when {
                            keywordA.isNotEmpty() && association.startsWith(keywordA, i, ignoreCase = true) -> {
                                pushStyle(SpanStyle(
                                    fontStyle = FontStyle.Italic,
                                    fontWeight = FontWeight.Bold,
                                    textDecoration = TextDecoration.Underline
                                ))
                                append(association.substring(i, i + keywordA.length))
                                pop()
                                i += keywordA.length
                            }
                            keywordB.isNotEmpty() && association.startsWith(keywordB, i, ignoreCase = true) -> {
                                pushStyle(SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    textDecoration = TextDecoration.Underline
                                ))
                                append(association.substring(i, i + keywordB.length).uppercase())
                                pop()
                                i += keywordB.length
                            }
                            else -> {
                                append(association[i])
                                i++
                            }
                        }
                    }
                }
            }

            Text(
                text = associationText,
                fontSize = CommonFontSizeMiddle,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}