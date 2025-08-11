package com.example.idioms.ui.screens.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.idioms.R
import com.example.idioms.ui.components.HeaderWithBack


@Composable
fun SignInScreen(
    navToHome: () -> Unit,
    navToSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .padding(dimensionResource(R.dimen.common_padding_default)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeaderWithBack(title = stringResource(R.string.sign_in))

        Spacer(Modifier.weight(1f))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.email)) },
            singleLine = true
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Button(
            onClick = { navToHome() },
            modifier = Modifier.padding(top = dimensionResource(R.dimen.common_padding_default))
        ) {
            Text(stringResource(R.string.sign_in))
        }
        Spacer(Modifier.weight(1f))

        TextButton(onClick = { navToSignUp() }) {
            Text(stringResource(R.string.advice_sign_up))
        }


    }

}



@Preview
@Composable
fun PreviewSignInScreen() {
    SignInScreen({},{})
}