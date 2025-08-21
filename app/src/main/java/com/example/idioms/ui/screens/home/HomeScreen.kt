package com.example.idioms.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.example.idioms.R
import com.example.idioms.ui.components.MyDropdown
import com.example.idioms.ui.components.TutorialDialog
import com.example.idioms.ui.models.Word
import com.example.idioms.utils.categoriesFilter
import com.example.idioms.utils.languagesFilter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navToGame: () -> Unit,
    navToEditWord: (Int) -> Unit,
    navToAddWord: () -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {

    val filteredWords by homeViewModel.filteredWords.collectAsState()
    val searchQuery by homeViewModel.searchQuery.collectAsState()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showFilters by remember { mutableStateOf(false) }

    var claseSeleccionada by remember { mutableStateOf(categoriesFilter[0]) }
    var idiomaOrigen by remember { mutableStateOf(languagesFilter[0]) }
    var idiomaDestino by remember { mutableStateOf(languagesFilter[0]) }

    var input by remember { mutableStateOf("") }
    var hasFocus by remember { mutableStateOf(false) }
    var selectedWord by remember { mutableStateOf<Word?>(null) }
    var showTutorial by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    var pendingOpenSheet by remember { mutableStateOf(false) }

    // Si el teclado no está visible, quita el foco
    LaunchedEffect(imeVisible) {
        if (!imeVisible && pendingOpenSheet) {
            showFilters= true
            pendingOpenSheet= false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { onToggleTheme() }
                    ) {
                        Icon(
                            imageVector = if (isDarkTheme)
                                Icons.Default.LightMode
                            else
                                Icons.Default.NightsStay,
                            contentDescription = "Toggle Theme"
                        )
                    }
                },
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        OutlinedTextField(
                            modifier = Modifier
                                .weight(1f)
                                .onFocusChanged { focusState ->
                                    hasFocus = focusState.isFocused
                                },
                            value = searchQuery/*input*/,
                            onValueChange = { homeViewModel.onSearchQueryChanged(it)/*input = it*/ },
                            placeholder = { Text(stringResource(R.string.txt_search)) },
                            leadingIcon = {
                                IconButton(onClick = {
                                    focusManager.clearFocus()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search"
                                    )
                                }
                            },
                            trailingIcon = {
                                if (hasFocus || input.isNotEmpty()) {
                                    IconButton(onClick = {
                                        input = ""
                                        focusManager.clearFocus()
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = "Clear"
                                        )
                                    }
                                }
                            },
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = dimensionResource(R.dimen.text_size_medium_plus).value.sp
                            ),
                            shape = RoundedCornerShape(dimensionResource(R.dimen.common_padding_min)),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color.Transparent
                            )

                        )
                        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.common_padding_min)))
                        Text(
                            text = "${filteredWords.size}",
                            fontSize = dimensionResource(R.dimen.text_size_medium_plus).value.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (imeVisible) {
                            // Si el teclado está abierto → cerrarlo y marcar que queremos abrir sheet
                            focusManager.clearFocus()
                            pendingOpenSheet = true
                        } else {
                            // Si ya está cerrado → abrir directamente
                            showFilters = true
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter"
                        )
                    }

                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(onClick = { navToAddWord() }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },

        bottomBar = {
            Surface(
                tonalElevation = dimensionResource(R.dimen.common_padding_mini),
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .height(dimensionResource(R.dimen.home_bottombar_height))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = dimensionResource(R.dimen.common_padding_default)),
                    horizontalArrangement = Arrangement
                        .spacedBy(dimensionResource(R.dimen.common_padding_large)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { showTutorial = true }) {
                        Icon(Icons.Default.School, contentDescription = "Tutorial")
                    }
                    Button(
                        onClick = { navToGame() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.btn_play_game),
                            fontSize = dimensionResource(R.dimen.text_size_medium_large).value.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

        }

    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = dimensionResource(R.dimen.common_padding_default))
        ) {
            items(filteredWords) { word ->
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.common_padding_min)))
                WordCard(
                    word = word,
                    onClick = { selectedWord = word })
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.common_padding_min)))
            }
        }

//        when (filteredWords) {
//            is WordsUiState.Error -> Text("Error al cargar rutinas")
//            WordsUiState.Loading -> CircularProgressIndicator(
//                //modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
//
//            is WordsUiState.Success -> {
//
//
//
//
//            }
//        }


        selectedWord?.let { word ->
            AlertDialog(
                onDismissRequest = { selectedWord = null },
                title = { Text(word.word) },
                text = {
                    Column {
                        Text(stringResource(R.string.translation, word.translation))
                        Text(stringResource(R.string.pronunciation, word.pronunciation))
                        Text(stringResource(R.string.association, word.association))
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        selectedWord?.let { word -> navToEditWord(word.id) }
                        selectedWord = null
                    }) {
                        Text(stringResource(R.string.txt_edit))
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = {
                            selectedWord?.let{ homeViewModel.onWordRemove(word) }
                            selectedWord = null
                        }
                    ) {
                        Text(stringResource(R.string.txt_delete))
                    }
                }
            )
        }

        if (showTutorial) {
            TutorialDialog(onExit = { showTutorial = false })
        }


        if (showFilters) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { showFilters = false }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(dimensionResource(R.dimen.common_padding_default))
                ) {
                    Text("Filtros", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(dimensionResource(R.dimen.common_padding_default)))

                    MyDropdown(
                        label = stringResource(R.string.categories),
                        options = categoriesFilter,
                        selectedOption = claseSeleccionada,
                        onOptionSelected = {
                            claseSeleccionada = it
                            homeViewModel.onCategorySelected(it)
                        },
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(R.dimen.common_padding_default),
                            vertical = dimensionResource(R.dimen.common_padding_min))
                    )

                    MyDropdown(
                        label = stringResource(R.string.original_language),
                        options = languagesFilter,
                        selectedOption = idiomaOrigen,
                        onOptionSelected = {
                            idiomaOrigen = it
                            homeViewModel.onOriginLangSelected(it)
                        },
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(R.dimen.common_padding_default),
                            vertical = dimensionResource(R.dimen.common_padding_min))
                    )
                    MyDropdown(
                        label = stringResource(R.string.translated_language),
                        options = languagesFilter,
                        selectedOption = idiomaDestino,
                        onOptionSelected = {
                            idiomaDestino = it
                            homeViewModel.onDestLangSelected(it)
                        },
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(R.dimen.common_padding_default),
                            vertical = dimensionResource(R.dimen.common_padding_min))
                    )
                }
            }
        }

    }

}

@Composable
private fun WordCard(
    onClick: () -> Unit,
    word: Word
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults
            .cardElevation(defaultElevation = dimensionResource(R.dimen.common_padding_mini))
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.common_padding_default))
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = word.word,
                fontSize = dimensionResource(R.dimen.text_size_medium_large).value.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.common_padding_min)))

            Text(
                text = word.translation,
                fontSize = dimensionResource(R.dimen.text_size_medium_plus).value.sp
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.common_padding_min)))

            Text(
                text = word.pronunciation,
                fontSize = dimensionResource(R.dimen.text_size_medium).value.sp
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.common_padding_min)))

            Text(
                text = word.association,
                fontSize = dimensionResource(R.dimen.text_size_medium).value.sp,
                textAlign = TextAlign.Start
            )

        }
    }
}

//
//@SuppressLint("ViewModelConstructorInComposable")
//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    val vm: WordsViewModel = WordsViewModel()
//
//    HomeScreen(wordsViewModel = vm, {}, {}, {},true,{})
//}
