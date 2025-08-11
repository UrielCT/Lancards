package com.example.idioms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.idioms.addwords.ui.WordsViewModel
import com.example.idioms.core.navigation.NavigationWrapper
import com.example.idioms.ui.theme.IdiomsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val wordsViewModel:WordsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IdiomsTheme {
                NavigationWrapper(wordsViewModel)
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

