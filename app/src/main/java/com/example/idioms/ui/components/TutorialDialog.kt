package com.example.idioms.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.idioms.R

@Composable
fun TutorialDialog( onExit:() -> Unit) {

    AlertDialog(
        modifier = Modifier.fillMaxWidth(),

        onDismissRequest = { onExit() },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.title_tutorial),
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

            Column(modifier = Modifier) {
                Text(
                    text = stringResource(R.string.title_description),
                    fontSize = dimensionResource(R.dimen.text_size_medium_plus).value.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = stringResource(R.string.txt_description))
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.common_padding_min)))

                Text(
                    text = stringResource(R.string.title_steps),
                    fontSize = dimensionResource(R.dimen.text_size_medium_plus).value.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = stringResource(R.string.txt_steps))
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.common_padding_min)))

                Text(
                    text = stringResource(R.string.title_example),
                    fontSize = dimensionResource(R.dimen.text_size_medium_plus).value.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Palabra en inglés: \"Carrot\" (zanahoria). Asociación inverosímil: " +
                            "Imagina a un \"carro\" con ruedas de zanahoria. Imagen mental: Un auto deportivo con zanahorias gigantes en lugar de llantas."
                )
            }

        },
        confirmButton = {}
    )

}





@Preview(showBackground = true)
@Composable
fun TutorialDialogPreview() {
    TutorialDialog({})
}
