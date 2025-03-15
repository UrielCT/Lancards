package com.example.idioms

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navigateToHome: () -> Unit/*navToSignUp: () -> Unit, onSignIn: (String, String) -> Unit*/) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sign In") }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation()
                )
                Button(onClick = { navigateToHome()/* onSignIn(email, password)*/ }, modifier = Modifier.padding(top = 8.dp)) {
                    Text("Sign In")
                }
                TextButton(onClick = {}/*navToSignUp*/) {
                    Text("Don't have an account? Sign Up")
                }
            }
        }
    )
}


/*
@Preview
@Composable
fun PreviewSignInScreen() {
    SignInScreen(navigateToHome () -> Unit/*navToSignUp = {}, onSignIn = { _, _ -> }*/)
}*/