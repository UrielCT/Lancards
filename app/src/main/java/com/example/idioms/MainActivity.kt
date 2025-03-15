package com.example.idioms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.idioms.core.navigation.NavigationWrapper
import com.example.idioms.ui.theme.IdiomsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IdiomsTheme {
                NavigationWrapper()
                //TutorialScreen()
                //HomeScreen()
                //AddWordScreen()
                //GameScreen()
                //SignInScreen()
                //SignUpScreen()
            }
        }
    }
}

