package com.example.idioms.ui.screens.game

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.idioms.R
import com.example.idioms.ui.components.MyDropdown
import com.example.idioms.ui.models.Word
import com.example.idioms.utils.GameState
import com.example.idioms.utils.words


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(navBack:() -> Unit) {
    var gameState by remember { mutableStateOf(GameState.STOPPED) }
    var correctWords by remember { mutableStateOf(0) }

    var order by remember { mutableStateOf("Mas Nuevas") }
    var game by remember { mutableStateOf("Recordar traducción") }
    var word by remember { mutableStateOf("") }
    var translation by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Todas") }
    var originalLang by remember { mutableStateOf("Todos") }
    var translatedLang by remember { mutableStateOf("Todos") }


    var isCheckEnabled by remember { mutableStateOf(true) }
    var isNextEnabled by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }

    val defaultTextColor = MaterialTheme.colorScheme.onBackground
    var textColor by remember { mutableStateOf(defaultTextColor) }

    var index by remember { mutableStateOf(0) }
    var wordsList = listOf<Word>()

    val originList = listOf("Todos") + words.map { it.originalLang }.distinct()
    val tradList = listOf("Todos") + words.map { it.translatedLang }.distinct()
    val categoryList = listOf("Todas") + words.map { it.category }.distinct()

    val orderList = listOf("Mas Nuevas","Mas Antiguas","Fecha Random", "Alfabético", "Alfabético Inverso", )
    val gameList = listOf("Recordar traducción", "Recordar palabra")


    var filteredWords = remember(originalLang, translatedLang, category, order) {
        words.filter {
            (originalLang.isEmpty() || originalLang == "Todos" || it.originalLang == originalLang) &&
                    (translatedLang.isEmpty() || translatedLang == "Todos" || it.translatedLang == translatedLang) &&
                    (category.isEmpty() || category == "Todas" || it.category == category)
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
    }

    val amountList = remember(filteredWords) {
        amount = 0.toString()
        (1..filteredWords.size).map { it.toString() }
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
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        Text(
                            text = stringResource(R.string.words_game),
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = dimensionResource(R.dimen.text_size_medium_large).value.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        },

        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.common_padding_default)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.common_padding_mini)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                when (gameState) {
                    GameState.STOPPED -> {

                        MyDropdown(
                            label = stringResource(R.string.original_language),
                            options = originList,
                            selectedOption = originalLang,
                            onOptionSelected = { originalLang = it },
                            modifier = Modifier.weight(1f)
                        )

                        MyDropdown(
                            label = stringResource(R.string.translated_language),
                            options = tradList,
                            selectedOption = translatedLang,
                            onOptionSelected = { translatedLang = it },
                            modifier = Modifier.weight(1f)
                        )

                        MyDropdown(
                            label = stringResource(R.string.categories),
                            options = categoryList,
                            selectedOption = category,
                            onOptionSelected = { category = it },
                            modifier = Modifier.weight(1f)
                        )

                        MyDropdown(
                            label = stringResource(R.string.order),
                            options = orderList,
                            selectedOption = order,
                            onOptionSelected = { order = it },
                            modifier = Modifier.weight(1f)
                        )

                        MyDropdown(
                            label = stringResource(R.string.amount),
                            options = amountList,
                            selectedOption = amount,
                            onOptionSelected = { amount = it },
                            modifier = Modifier.weight(1f)
                        )

                        MyDropdown(
                            label = stringResource(R.string.game),
                            options = gameList,
                            selectedOption = game,
                            onOptionSelected = { game = it },
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier
                            .height(dimensionResource(R.dimen.common_padding_min)))
                        Text(
                            text = stringResource(R.string.filtered_words, filteredWords.size),
                            fontSize = dimensionResource(R.dimen.text_size_medium).value.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier
                            .height(dimensionResource(R.dimen.common_padding_min)))

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    wordsList = filteredWords
                                    filteredWords = filteredWords.take(amount.toInt())
                                    gameState = GameState.RUNNING
                                          },
                                enabled = filteredWords.isNotEmpty() && amount > 0.toString()
                        ) {
                            Text(
                                text = stringResource(R.string.play),
                                fontSize = dimensionResource(R.dimen.text_size_medium_large).value.sp)
                        }

                    }

                    GameState.RUNNING -> {

                        Text(
                            text = "${index + 1} / ${filteredWords.size}",
                            fontSize = dimensionResource(R.dimen.text_size_medium_large).value.sp,
                            fontWeight = FontWeight.Bold,
                            color =  Color.LightGray
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.common_padding_default)))

                        Text(
                            text = getText(game, index, filteredWords.size,
                                filteredWords[index].word, filteredWords[index].translation),
                            fontSize = dimensionResource(R.dimen.text_size_large_plus).value.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.common_padding_default)))

                        Text(
                            text = getCorrectText(game, isCorrect, filteredWords[index].word,
                                filteredWords[index].translation),
                            fontSize = dimensionResource(R.dimen.text_size_large).value.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Green
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.common_padding_default)))
                        //si juego es 1- poner palabra,si es 2- poner traduccion
                        OutlinedTextField(
                            value = if (game == "Recordar traducción") translation else word,
                            onValueChange = {
                                val sanitizedText = it.replace("\n", "").replace("\r", "") // Elimina saltos de línea
                                if (game == "Recordar traducción") translation = sanitizedText else word = sanitizedText
                            },
                            label = { Text("Ingresá la forma correcta") }
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.common_padding_default)))

                        Row(
                            horizontalArrangement = Arrangement
                                .spacedBy(dimensionResource(R.dimen.common_padding_default)),
                        ) {
                            Button(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    if(game == "Recordar traducción"){
                                        if(translation.replace(" ", "")
                                            .uppercase() == filteredWords[index].translation
                                                .replace(" ", "").uppercase()){
                                            correctWords++
                                            isCorrect = true
                                            textColor = Color.Green
                                        }else {
                                            // Agregar a una lista de palabras para practicar
                                            isCorrect = false
                                            textColor = Color.Red
                                        }
                                    }else if(game == "Recordar palabra"){
                                        if(word.replace(" ", "")
                                            .uppercase() == filteredWords[index].word
                                                .replace(" ", "").uppercase()){
                                            correctWords++
                                            isCorrect = true
                                            textColor = Color.Green
                                        }else {
                                            isCorrect = false
                                            textColor = Color.Red
                                        }
                                    }

                                    isCheckEnabled = false
                                    isNextEnabled = true
                                },
                                enabled = isCheckEnabled
                            ) {
                                Text(stringResource(R.string.check))
                            }

                            Button(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    translation = ""
                                    word = ""
                                    isCorrect = null
                                    textColor = defaultTextColor
                                    isCheckEnabled = true
                                    isNextEnabled = false
                                    if (index < filteredWords.size - 1) {
                                        index++
                                    } else {
                                        gameState = GameState.FINISHED
                                        index = 0
                                    }
                                },
                                enabled = isNextEnabled
                            ) {
                                Text(stringResource(R.string.next))
                            }

                        }
                    }

                    GameState.FINISHED -> {

                        Text(
                            text = stringResource(
                                R.string.correct_words_text,
                                correctWords,
                                filteredWords.size
                            ),
                            fontSize = dimensionResource(R.dimen.text_size_medium_large).value.sp,
                            fontWeight = FontWeight.Bold)

                        Spacer(Modifier.height(dimensionResource(R.dimen.common_padding_default)))

                        Text(
                            text = stringResource(R.string.order_text, order),
                            fontSize = dimensionResource(R.dimen.text_size_medium_large).value.sp,
                            fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.common_padding_default)))

                        Text(
                            text = stringResource(R.string.game_text, game),
                            fontSize = dimensionResource(R.dimen.text_size_medium_large).value.sp,
                            fontWeight = FontWeight.Bold)

                        Spacer(Modifier.height(dimensionResource(R.dimen.common_padding_default)))

                        Button(onClick = {
                            filteredWords = wordsList
                            correctWords = 0
                            gameState = GameState.STOPPED
                        }) {
                            Text(stringResource(R.string.again))
                        }

                    }
                }


            }
        }
    )
}

fun getText(juego: String, index: Int, size: Int, palabra: String, traduccion: String): String {
    return if (index < size) {
        if (juego == "Recordar traducción") palabra else traduccion
    } else {
        "Fin"
    }
}

fun getCorrectText(juego: String, isCorrect: Boolean?, palabra: String, traduccion: String): String {
   return if (juego == "Recordar traducción"){
       if(isCorrect == false) traduccion else ""
   } else {
       if(isCorrect == false) palabra else ""
   }
}
