package com.example.idioms.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import com.example.idioms.ui.theme.CommonPaddingDefault
import com.example.idioms.ui.theme.CommonPaddingLarge_lm
import com.example.idioms.ui.theme.CommonPaddingMicro
import com.example.idioms.ui.theme.CommonPaddingMin
import com.example.idioms.ui.theme.CommonPaddingMinDefault
import com.example.idioms.ui.theme.CommonPaddingOne

@Composable
fun MyEditText(
    value: String,
    label: Int,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true
) {
    var textFieldValue by remember(value) {
        mutableStateOf(
            TextFieldValue(
                text = value,
                selection = TextRange(value.length)
            )
        )
    }


    LaunchedEffect(value) {
        if (value != textFieldValue.text) {
            textFieldValue = textFieldValue.copy(
                text = value,
                selection = TextRange(value.length)
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = CommonPaddingMin, horizontal = CommonPaddingDefault)
    ) {
        Text(
            text = stringResource(label),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(CommonPaddingLarge_lm)
                .padding(vertical = CommonPaddingMicro)
                .border(
                    CommonPaddingOne,
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(CommonPaddingMin)
                )
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(CommonPaddingMin))
                .padding(horizontal = CommonPaddingMinDefault, vertical = CommonPaddingMin),
            contentAlignment = Alignment.CenterStart
        ) {

            BasicTextField(
                enabled = enabled,
                value = textFieldValue,
                singleLine = singleLine,
                onValueChange = {
                    textFieldValue = it
                    onValueChange(it.text)
                },
                textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = keyboardOptions.copy(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                keyboardActions = keyboardActions,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
            )
        }
    }
}