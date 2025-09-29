package com.example.idioms.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.idioms.R
import com.example.idioms.ui.theme.CommonPaddingDefault
import com.example.idioms.ui.theme.CommonPaddingMin
import com.example.idioms.utils.categoriesFilter
import com.example.idioms.utils.languagesFilter
import com.example.idioms.utils.orderList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersBottomSheet(
    selectedCategory: String,
    selectedOrder: String,
    originalLanguage: String,
    translationLanguage: String,
    onDismissRequest:() -> Unit,
    onCategorySelected: (String) -> Unit,
    onOrderSelected: (String) -> Unit,
    onOriginalLangSelected: (String) -> Unit,
    onTranslLangSelected: (String) -> Unit,
){
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismissRequest() },
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = CommonPaddingMin
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                stringResource(R.string.title_filters), style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface)
            Spacer(Modifier.height(CommonPaddingDefault))

            LanguagesView(
                onOLWordChange = { onOriginalLangSelected(it) },
                onTLWordChange = { onTranslLangSelected(it) },
                languages = languagesFilter,
                originalSelectedOption = originalLanguage,
                translSelectedOption = translationLanguage
            )

            MyDropdown(
                label = stringResource(R.string.categories),
                options = categoriesFilter,
                selectedOption = selectedCategory,
                onOptionSelected = { onCategorySelected(it) },
                modifier = Modifier.padding(vertical = CommonPaddingMin, horizontal = CommonPaddingDefault),
            )

            MyDropdown(
                label = stringResource(R.string.order),
                options = orderList,
                selectedOption = selectedOrder,
                onOptionSelected = { onOrderSelected(it) },
                modifier = Modifier.padding(vertical = CommonPaddingMin, horizontal = CommonPaddingDefault),
            )

        }
    }
}