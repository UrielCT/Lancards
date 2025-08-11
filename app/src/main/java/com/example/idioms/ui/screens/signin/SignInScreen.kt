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
import androidx.compose.ui.unit.sp
import com.example.idioms.R


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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Sign In",
            fontSize = dimensionResource(R.dimen.text_size_large).value.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.weight(1f))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.email)) }
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = { navToHome() },
            modifier = Modifier.padding(top = dimensionResource(R.dimen.common_padding_min))
        ) {
            Text(stringResource(R.string.btn_sign_in))
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