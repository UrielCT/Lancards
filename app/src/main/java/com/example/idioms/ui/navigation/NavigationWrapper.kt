@file:Suppress("DEPRECATION")

package com.example.idioms.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.idioms.ui.components.CustomSnackBar
import com.example.idioms.ui.components.CustomSnackBarState
import com.example.idioms.ui.screens.addword.AddWordScreen
import com.example.idioms.ui.screens.game.GameScreen
import com.example.idioms.ui.screens.home.HomeScreen
import com.example.idioms.ui.screens.addword.WordsViewModel
import com.example.idioms.ui.screens.game.GameViewModel
import com.example.idioms.ui.screens.home.HomeViewModel
import com.example.idioms.ui.theme.SnackBarPaddingBottom
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun NavigationWrapper(
    wordsViewModel: WordsViewModel,
    homeViewModel: HomeViewModel,
    gameViewModel: GameViewModel,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isDarkTheme
    val surface = MaterialTheme.colorScheme.surface
    val background = MaterialTheme.colorScheme.background

    val snackBarState = remember { CustomSnackBarState() }

    LaunchedEffect(isDarkTheme) {
        systemUiController.setStatusBarColor(
            color = surface,
            darkIcons = useDarkIcons
        )
        systemUiController.setNavigationBarColor(
            color = background,
            darkIcons = useDarkIcons
        )
    }

        Box(Modifier.fillMaxSize()) {

            NavHost(
                navController = navController,
                startDestination = Home,
            ){

                composable<Home> {
                    HomeScreen(
                        homeViewModel = homeViewModel,
                        navToGame = { navController.navigate(Game) },
                        navToEditWord = { wordId -> navController.navigate(EditWord(wordId)) },
                        navToAddWord = { navController.navigate(AddWord) },
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = onToggleTheme,
                        showSnackBar = { msg -> snackBarState.showMessage(msg) }
                    )
                }

                composable<Game>{
                    GameScreen(
                        gameViewModel = gameViewModel,
                        navBack = { navController.popBackStack() }
                    )
                }

                composable<EditWord>{ backStackEntry ->
                    val args = backStackEntry.toRoute<EditWord>()
                    AddWordScreen(
                        wordsViewModel = wordsViewModel,
                        navBack = { navController.popBackStack() },
                        wordId = args.wordId,
                        showSnackBar = { msg -> snackBarState.showMessage(msg) }
                    )
                }

                composable<AddWord>{
                    AddWordScreen(
                        wordsViewModel=wordsViewModel,
                        navBack = { navController.popBackStack() },
                        showSnackBar = { msg -> snackBarState.showMessage(msg) }
                    )
                }
            }

            CustomSnackBar(
                state = snackBarState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = SnackBarPaddingBottom)
            )

    }
}