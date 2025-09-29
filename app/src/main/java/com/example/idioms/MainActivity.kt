package com.example.idioms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.idioms.ui.screens.addword.WordsViewModel
import com.example.idioms.ui.navigation.NavigationWrapper
import com.example.idioms.ui.screens.game.GameViewModel
import com.example.idioms.ui.screens.home.HomeViewModel
import com.example.idioms.ui.theme.IdiomsTheme
import com.example.idioms.ui.viewmodels.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val wordsViewModel: WordsViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val gameViewModel: GameViewModel by viewModels()
    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            IdiomsTheme(darkTheme = isDarkTheme) {
                NavigationWrapper(
                    wordsViewModel = wordsViewModel,
                    homeViewModel = homeViewModel,
                    gameViewModel = gameViewModel,
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { themeViewModel.toggleTheme() }
                )
            }
        }
    }
}

