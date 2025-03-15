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
fun SignUpScreen(/*navToSignIn: () -> Unit, onSignUp: (String, String) -> Unit*/) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Sign Up") }) },
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
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    visualTransformation = PasswordVisualTransformation()
                )
                Button(onClick = { /*onSignUp(email, password)*/ }, modifier = Modifier.padding(top = 8.dp)) {
                    Text("Sign Up")
                }
                TextButton(onClick = {}/*navToSignIn*/) {
                    Text("Already have an account? Sign In")
                }
            }
        }
    )
}


@Preview
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen(/*navToSignIn = {}, onSignUp = { _, _ -> }*/)
}