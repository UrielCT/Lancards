@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.idioms.ui.screens.addword

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.idioms.R
import com.example.idioms.ui.components.MyDropdown
import com.example.idioms.ui.models.Word
import com.example.idioms.utils.categories
import com.example.idioms.utils.languages

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWordScreen(
    navBack: () -> Unit,
    wordsViewModel: WordsViewModel,
    wordId: Int? = null
) {
    val context = LocalContext.current

    var word by remember { mutableStateOf("") }
    var translation by remember { mutableStateOf("") }
    var pronunciation by remember { mutableStateOf("") }
    var association by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    var originalLanguage by remember { mutableStateOf(languages[0]) }
    var translatedLanguage by remember { mutableStateOf(languages[1]) }
    var keywordA by remember { mutableStateOf("") }
    var keywordB by remember { mutableStateOf("") }
    var keywordC by remember { mutableStateOf("") }


    //cargar ejercicio al iniciar
    LaunchedEffect(wordId) {
        if(wordId != null){
            wordsViewModel.onGetWordById(wordId)
        }else{
            // Si es nueva palabra, reseteamos estado
            wordsViewModel.resetSelectedWord()
        }
    }

    // Obtener palabra a editar
    val wordUiState by wordsViewModel.selectedWordState.collectAsState()

    val wordToEdit = (wordUiState as? WordUiState.Success)?.word

    LaunchedEffect(wordToEdit) {
        word = wordToEdit?.word ?: ""
        translation = wordToEdit?.translation ?: ""
        pronunciation = wordToEdit?.pronunciation ?: ""
        association = wordToEdit?.association ?: ""
        selectedCategory = wordToEdit?.category ?: categories[0]
        originalLanguage = wordToEdit?.originalLang ?: languages[0]
        translatedLanguage = wordToEdit?.translatedLang ?: languages[1]
        keywordA = wordToEdit?.keyWordA ?: ""
        keywordB = wordToEdit?.keyWordB ?: ""
        keywordC = wordToEdit?.keyWordC ?: ""
    }

    LaunchedEffect(wordId) {
        if (wordId != null) {
            wordsViewModel.onGetWordById(wordId)
        }else{
            word = ""
            translation = ""
            pronunciation = ""
            association = ""
            selectedCategory = categories[0]
            originalLanguage = languages[0]
            translatedLanguage = languages[1]
            keywordA = ""
            keywordB = ""
            keywordC = ""
        }
    }

    val focusManager = LocalFocusManager.current
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    // Si el teclado no está visible, quita el foco
    LaunchedEffect(imeVisible) {
        if (!imeVisible) focusManager.clearFocus()
    }

    fun saveWord(){
        val idToUse = if (wordId != null) wordToEdit?.id ?: 0 else 0
        val dateToUse = wordToEdit?.date ?: System.currentTimeMillis().toString()

        val newWord = Word(
            id = idToUse,
            date = dateToUse,
            word = word,
            translation = translation,
            pronunciation = pronunciation,
            association = association,
            originalLang = originalLanguage,
            translatedLang = translatedLanguage,
            keyWordA = keywordA,
            keyWordB = keywordB,
            keyWordC = keywordC,
            category = selectedCategory
        )

        if (wordId == null) {
            wordsViewModel.onWordCreated(newWord)
            Toast.makeText(context, "Palabra agregada", Toast.LENGTH_SHORT).show()
            wordsViewModel.resetSelectedWord()
        } else {
            wordsViewModel.onWordUpdated(newWord)
            Toast.makeText(context, "Palabra actualizada", Toast.LENGTH_SHORT).show()
        }
        navBack()
    }


    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(
                            onClick = { navBack() },
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        Text(
                            text = if (wordId == null) "Add new Word" else "Edit Word",
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = dimensionResource(R.dimen.text_size_medium_large).value.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        // Solo mostrar ProgressIndicator si estamos editando y está cargando
        if (wordId != null && wordUiState is WordUiState.Loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (wordId != null && wordUiState is WordUiState.Error) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error al cargar la palabra", color = Color.Red)
            }
        } else {
            // Formulario de palabra (tanto para editar como agregar)
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.common_padding_default)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.common_padding_mini))
            ) {
                MyEditText(value = word, label = stringResource(R.string.tf_word)) { word = it }
                MyEditText(value = translation, label = stringResource(R.string.tf_translation)) { translation = it }
                MyEditText(value = pronunciation, label = stringResource(R.string.tf_pronunciation)) { pronunciation = it }
                MyEditText(value = association, label = stringResource(R.string.tf_association)) { association = it }

                MyDropdown(
                    label = stringResource(R.string.categories),
                    options = categories,
                    selectedOption = selectedCategory,
                    onOptionSelected = { selectedCategory = it }
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.common_padding_min)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    MyDropdown(
                        label = stringResource(R.string.original_language),
                        options = languages,
                        selectedOption = originalLanguage,
                        onOptionSelected = { originalLanguage = it },
                        modifier = Modifier.weight(1f)
                    )
                    MyDropdown(
                        label = stringResource(R.string.translated_language),
                        options = languages,
                        selectedOption = translatedLanguage,
                        onOptionSelected = { translatedLanguage = it },
                        modifier = Modifier.weight(1f)
                    )
                }

                MyEditText(value = keywordA, label = stringResource(R.string.tf_keyword_a)) { keywordA = it }
                MyEditText(value = keywordB, label = stringResource(R.string.tf_keyword_b)) { keywordB = it }
                MyEditText(value = keywordC, label = stringResource(R.string.tf_keyword_c)) { keywordC = it }

                Button(
                    onClick = { saveWord() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.common_padding_default))
                ) {
                    Text(if (wordId == null) stringResource(R.string.add_word) else "Actualizar palabra")
                }
            }
        }

    }
}


@Composable
private fun MyEditText(value:String, label: String,onValueChange: (String) -> Unit ){
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange( it ) },
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1
    )
}
