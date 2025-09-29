package com.example.idioms.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.idioms.R
import com.example.idioms.ui.theme.CommonPaddingDefault
import com.example.idioms.ui.theme.CommonPaddingMicro
import com.example.idioms.ui.theme.CommonPaddingMin

@Composable
fun LanguagesView(
    onOLWordChange:(String)-> Unit,
    onTLWordChange:(String)-> Unit,
    languages:List<String>,
    originalSelectedOption: String,
    translSelectedOption: String,

){
    Column(modifier = Modifier.padding(horizontal = CommonPaddingDefault),
        verticalArrangement = Arrangement.spacedBy(CommonPaddingMin)) {

        Text(stringResource(R.string.title_languages), style = MaterialTheme.typography.labelLarge)
        Row(
            horizontalArrangement = Arrangement.spacedBy(CommonPaddingMicro),
            modifier = Modifier.fillMaxWidth()
        ) {
            MyDropdown(
                label = stringResource(R.string.original_language),
                options = languages,
                selectedOption = originalSelectedOption,
                onOptionSelected = { onOLWordChange(it) },
                modifier = Modifier.weight(1f)
            )
            MyDropdown(
                label = stringResource(R.string.translated_language),
                options = languages,
                selectedOption = translSelectedOption,
                onOptionSelected = { onTLWordChange(it) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

