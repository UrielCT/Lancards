package com.example.idioms.ui.screens.game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.example.idioms.R
import com.example.idioms.domain.models.Word
import com.example.idioms.ui.components.MyEditText
import com.example.idioms.ui.theme.CommonFontSizeLarge
import com.example.idioms.ui.theme.CommonPaddingDefault
import com.example.idioms.ui.theme.CommonPaddingMin
import com.example.idioms.ui.theme.MessageVerticalSpace
import com.example.idioms.utils.gameList

@Composable
fun RunningView(
    game: String,
    index: Int,
    filteredWords: List<Word>,
    translation: String,
    word: String,
    isCorrect: Boolean?,
    isCheckEnabled: Boolean,
    isNextEnabled: Boolean,
    onTranslationChange: (String) -> Unit,
    onWordChange: (String) -> Unit,
    onCheck: (Boolean, Color) -> Unit,
    onNext: () -> Unit,
) {
    val currentWord = filteredWords.getOrNull(index)
    val gameOption = gameList[0]

    fun getShownWord(word: Word): String =
        if (game == gameOption) word.word else word.translation

    fun getCorrectWord(word: Word): String =
        if (game == gameOption) word.translation else word.word

    Column(modifier = Modifier.padding(horizontal = CommonPaddingDefault),
        horizontalAlignment = Alignment.CenterHorizontally) {

        if (currentWord != null) {
            Text(
                text = getShownWord(currentWord),
                fontSize = CommonFontSizeLarge,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MessageVerticalSpace)
            ) {
                val userInput = if (game == gameOption) translation else word
                val correctWord = getCorrectWord(currentWord)

                if (!isCheckEnabled) {
                    when {
                        isCorrect == true -> {
                            // ✅ Correct
                            Text(
                                text = userInput,
                                color = Color.Green,
                                fontSize = CommonFontSizeLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        userInput.isBlank() -> {
                            // ⚠ empty
                            Text(
                                text = "( )",
                                color = Color.Red,
                                fontSize = CommonFontSizeLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(" → ", fontSize = CommonFontSizeLarge)
                            Text(
                                text = correctWord,
                                color = Color.Green,
                                fontSize = CommonFontSizeLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        else -> {
                            // ❌ Incorrect
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            color = Color.Red,
                                            textDecoration = TextDecoration.LineThrough
                                        )
                                    ) {
                                        append(userInput)
                                    }
                                },
                                fontSize = CommonFontSizeLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(" → ", fontSize = CommonFontSizeLarge)
                            Text(
                                text = correctWord,
                                color = Color.Green,
                                fontSize = CommonFontSizeLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                } else {
                    Text("")
                }
            }


            Spacer(modifier = Modifier.height(CommonPaddingMin))

            MyEditText(
                value = if (game == gameOption) translation else word,
                label = R.string.tf_correct_word,
                onValueChange = {
                    val sanitized = it.replace("\n", "").replace("\r", "")
                    if (game == gameOption) onTranslationChange(sanitized)
                    else onWordChange(sanitized)
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Done
                ),
                enabled = isCheckEnabled
            )

            Spacer(modifier = Modifier.height(CommonPaddingMin))

            Row(horizontalArrangement = Arrangement.spacedBy(CommonPaddingDefault)) {
                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = {
                        val correctWord = getCorrectWord(currentWord)
                        val userInput = if (game == gameOption) translation else word

                        val isCorrectNow = userInput.trim()
                            .equals(correctWord.trim(), ignoreCase = true)

                        onCheck(isCorrectNow, if (isCorrectNow) Color.Green else Color.Red)
                    },
                    enabled = isCheckEnabled
                ) {
                    Text(stringResource(R.string.check), fontSize = CommonFontSizeLarge)
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onNext() },
                    enabled = isNextEnabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(stringResource(R.string.next), fontSize = CommonFontSizeLarge)
                }
            }
        }
    }
}