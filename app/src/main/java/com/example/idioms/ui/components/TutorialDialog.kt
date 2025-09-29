package com.example.idioms.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.idioms.R
import com.example.idioms.ui.theme.AlertDialogWidthInMax
import com.example.idioms.ui.theme.CommonFontSizeDefault
import com.example.idioms.ui.theme.CommonFontSizeDefaultMid
import com.example.idioms.ui.theme.CommonFontSizeLarge
import com.example.idioms.ui.theme.CommonFontSizeMiddle
import com.example.idioms.ui.theme.CommonFontSizeMiddleMin
import com.example.idioms.ui.theme.CommonFontSizeMin
import com.example.idioms.ui.theme.CommonPaddingDefault
import com.example.idioms.ui.theme.CommonPaddingMiddle
import com.example.idioms.ui.theme.CommonPaddingMin
import com.example.idioms.ui.theme.MediumScreenWidth
import com.example.idioms.ui.theme.SmallScreenWidth
import com.example.idioms.utils.descriptionExample

@Composable
fun TutorialDialog( onExit:() -> Unit) {

    AlertDialog(
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = CommonPaddingDefault, vertical = CommonPaddingMiddle)
            .widthIn(max = AlertDialogWidthInMax),
        onDismissRequest = { onExit() },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.title_tutorial),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
                IconButton(
                    onClick = { onExit() },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
            }

        },
        text = {

            BoxWithConstraints {
                val screenWidth = maxWidth
                val titleSize = when {
                    screenWidth < SmallScreenWidth -> CommonFontSizeMiddle
                    screenWidth < MediumScreenWidth -> CommonFontSizeDefault
                    else -> CommonFontSizeLarge
                }
                val bodySize = when {
                    screenWidth < SmallScreenWidth -> CommonFontSizeMin
                    screenWidth < MediumScreenWidth -> CommonFontSizeMiddleMin
                    else -> CommonFontSizeDefaultMid
                }

                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = stringResource(R.string.title_description),
                        fontSize = titleSize,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.txt_description),
                        fontSize = bodySize
                    )
                    Spacer(modifier = Modifier.height(CommonPaddingMin))

                    Text(
                        text = stringResource(R.string.title_steps),
                        fontSize = titleSize,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.txt_steps),
                        fontSize = bodySize
                    )
                    Spacer(modifier = Modifier.height(CommonPaddingMin))

                    Text(
                        text = stringResource(R.string.title_example),
                        fontSize = titleSize,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = descriptionExample,
                        fontSize = bodySize
                    )
                }
            }

        },
        confirmButton = {}
    )
}