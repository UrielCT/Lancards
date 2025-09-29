package com.example.idioms.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.idioms.R
import com.example.idioms.domain.models.Word
import com.example.idioms.ui.theme.CommonPaddingMin

@Composable
fun EditDeleteDialog(
    word: Word,
    onDismissRequest:()-> Unit,
    onEditClick:() -> Unit,
    onDeleteClick:() -> Unit
){
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurface,
        title = { Text(word.word) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(CommonPaddingMin)) {
                Text(stringResource(R.string.translation," " + word.translation))
                Text(stringResource(R.string.pronunciation," " + word.pronunciation))
                Text(stringResource(R.string.association," " + word.association))
            }
        },
        confirmButton = {
            Button(
                onClick = { onEditClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(stringResource(R.string.txt_edit))
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = { onDeleteClick() },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(stringResource(R.string.txt_delete))
            }
        }
    )
}