package com.example.idioms.ui.screens.game.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.idioms.R
import com.example.idioms.ui.theme.CommonFontSizeLarge
import com.example.idioms.ui.theme.CommonPaddingDefault

@Composable
fun FinishedView(
    correctWords: Int,
    filteredWordsSize: Int,
    order: String,
    game: String,
    onRestart: () -> Unit
) {

    Column(modifier = Modifier.padding(CommonPaddingDefault)) {
        Text(
            text = stringResource(R.string.correct_words_text, correctWords, filteredWordsSize),
            fontSize = CommonFontSizeLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(CommonPaddingDefault))

        Text(
            text = stringResource(R.string.order_text, order),
            fontSize = CommonFontSizeLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(CommonPaddingDefault))

        Text(
            text = stringResource(R.string.game_text, game),
            fontSize = CommonFontSizeLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.weight(1f))

        Button(
            onClick = onRestart,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary)
        ) { Text(stringResource(R.string.again), fontSize = CommonFontSizeLarge) }
    }
}
