package com.example.idioms.ui.screens.signup

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navToHome: () -> Unit,
    navBack: () -> Unit,
/*onSignUp: (String, String) -> Unit*/
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(Modifier.fillMaxWidth()) {
                    IconButton(onClick = {}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")
                    }
                    Text("Sign Up")
                }


                Spacer(Modifier.weight(1f))

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
                Button(onClick = { /*onSignUp(email, password)*/ },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Sign Up")
                }
                TextButton(onClick = { navToHome() }/*navToSignIn*/) {
                    Text("Already have an account? Sign In")
                }

                Spacer(Modifier.weight(1f))

            }
        }
    )
}


@Preview
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen({},{})
}