package com.example.idioms.ui.screens.game.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.idioms.R
import com.example.idioms.domain.models.Word
import com.example.idioms.ui.components.LanguagesView
import com.example.idioms.ui.components.MyDropdown
import com.example.idioms.ui.theme.CommonFontSizeLarge
import com.example.idioms.ui.theme.CommonPaddingDefault
import com.example.idioms.ui.theme.CommonPaddingMin
import com.example.idioms.ui.theme.GameBottomPadding
import com.example.idioms.utils.categoriesFilter
import com.example.idioms.utils.gameList
import com.example.idioms.utils.languagesFilter
import com.example.idioms.utils.orderList

@Composable
fun StoppedView(
    originalLang: String,
    translatedLang: String,
    category: String,
    order: String,
    amount: String,
    game: String,
    amountList: List<String>,
    filteredWords: List<Word>,
    onOriginalLangChange: (String) -> Unit,
    onTranslatedLangChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onOrderChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onGameChange: (String) -> Unit,
    onStartClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = GameBottomPadding)
        ) {

            item{
                LanguagesView(
                    onOLWordChange = onOriginalLangChange,
                    onTLWordChange = onTranslatedLangChange,
                    languages = languagesFilter,
                    originalSelectedOption = originalLang,
                    translSelectedOption = translatedLang
                )
            }
            item {
                MyDropdown(
                    modifier = Modifier.padding(vertical = CommonPaddingMin,
                        horizontal = CommonPaddingDefault),
                    label = stringResource(R.string.categories),
                    options = categoriesFilter,
                    selectedOption = category,
                    onOptionSelected = onCategoryChange
                )
            }
            item {
                MyDropdown(
                    modifier = Modifier.padding(vertical = CommonPaddingMin,
                        horizontal = CommonPaddingDefault),
                    label = stringResource(R.string.order),
                    options = orderList,
                    selectedOption = order,
                    onOptionSelected = onOrderChange
                )
            }
            item {
                MyDropdown(
                    modifier = Modifier.padding(vertical = CommonPaddingMin,
                        horizontal = CommonPaddingDefault),
                    label = stringResource(R.string.amount),
                    options = amountList,
                    selectedOption = amount,
                    onOptionSelected = onAmountChange
                )
            }
            item {
                MyDropdown(
                    modifier = Modifier.padding(vertical = CommonPaddingMin,
                        horizontal = CommonPaddingDefault),
                    label = stringResource(R.string.game),
                    options = gameList,
                    selectedOption = game,
                    onOptionSelected = onGameChange
                )
            }

        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = CommonPaddingMin, horizontal = CommonPaddingDefault)
        ) {
            Text(
                text = stringResource(R.string.filtered_words, filteredWords.size),
                fontSize = CommonFontSizeLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(CommonPaddingMin))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onStartClick,
                enabled = filteredWords.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = stringResource(R.string.play),
                    fontSize = CommonFontSizeLarge
                )
            }
        }
    }
}
