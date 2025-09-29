package com.example.idioms.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.example.idioms.R
import com.example.idioms.domain.models.Word
import com.example.idioms.ui.theme.CommonPaddingDefault
import com.example.idioms.ui.theme.CommonPaddingMin
import com.example.idioms.utils.categories
import com.example.idioms.utils.languages

@Composable
fun WordForm(
    paddingValues: PaddingValues,
    word: Word,
    onWordChange: (Word) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .imePadding()

            .verticalScroll(rememberScrollState()),
    ) {
        MyEditText(
            value = word.word,
            label = R.string.tf_word,
            onValueChange = { onWordChange(word.copy(word = it)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )

        MyEditText(
            value = word.translation,
            label = R.string.tf_translation,
            onValueChange = { onWordChange(word.copy(translation = it)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )

        MyEditText(
            value = word.pronunciation,
            label = R.string.tf_pronunciation,
            onValueChange = { onWordChange(word.copy(pronunciation = it)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )

        MyEditText(
            value = word.association,
            label = R.string.tf_association,
            onValueChange = { onWordChange(word.copy(association = it)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done
            )
        )

        LanguagesView(
            onOLWordChange = { onWordChange(word.copy(originalLang = it)) },
            onTLWordChange = { onWordChange(word.copy(translatedLang = it)) },
            languages = languages,
            originalSelectedOption = word.originalLang,
            translSelectedOption = word.translatedLang,
        )

        MyDropdown(
            modifier = Modifier.padding(vertical = CommonPaddingMin, horizontal = CommonPaddingDefault),
            label = stringResource(R.string.categories),
            options = categories,
            selectedOption = word.category,
            onOptionSelected = { onWordChange(word.copy(category = it)) }
        )

        MyEditText(
            value = word.keyWordA,
            label = R.string.tf_keyword_a,
            onValueChange = { onWordChange(word.copy(keyWordA = it)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )

        MyEditText(
            value = word.keyWordB,
            label = R.string.tf_keyword_b,
            onValueChange = { onWordChange(word.copy(keyWordB = it)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done
            )
        )
    }
}