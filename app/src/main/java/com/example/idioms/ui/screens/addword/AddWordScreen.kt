@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.idioms.ui.screens.addword

import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.idioms.R
import com.example.idioms.ui.components.MyDropdown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWordScreen(navBack: () -> Unit) {
    val context = LocalContext.current

    val categories = listOf("Verbos", "Sustantivos", "Adjetivos")
    val languages = listOf("Español", "Inglés", "Francés", "Alemán")

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

    val focusManager = LocalFocusManager.current

    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    // Si el teclado no está visible, quita el foco
    LaunchedEffect(imeVisible) {
        if (!imeVisible) {
            focusManager.clearFocus()
        }
    }


    Scaffold(
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
                            text = "Add new Word",
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

            // Idiomas en fila
            Row(horizontalArrangement = Arrangement
                .spacedBy(dimensionResource(R.dimen.common_padding_min)),
                modifier = Modifier.fillMaxWidth()) {

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
                onClick = {
                    Toast.makeText(context, "Palabra agregada", Toast.LENGTH_SHORT).show()
                    navBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(R.dimen.common_padding_default))
            ) {
                Text(stringResource(R.string.add_word))
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



@Preview
@Composable
fun AddWordScreenPreview() {
    AddWordScreen({})
}
