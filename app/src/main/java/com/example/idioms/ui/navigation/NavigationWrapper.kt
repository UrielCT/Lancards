package com.example.idioms.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.idioms.ui.screens.addword.AddWordScreen
import com.example.idioms.ui.screens.game.GameScreen
import com.example.idioms.ui.screens.home.HomeScreen
import com.example.idioms.ui.screens.signin.SignInScreen
import com.example.idioms.ui.screens.signup.SignUpScreen
import com.example.idioms.ui.screens.addword.WordsViewModel
import com.example.idioms.ui.screens.game.GameViewModel
import com.example.idioms.ui.screens.home.HomeViewModel

@Composable
fun NavigationWrapper(
    wordsViewModel: WordsViewModel,
    homeViewModel: HomeViewModel,
    gameViewModel: GameViewModel,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SignIn){

        composable<SignIn>{
            SignInScreen(
                navToHome = { navController.navigate(Home) },
                navToSignUp = { navController.navigate(SignUp) },
            )
        }
        composable<SignUp>{
            SignUpScreen(
                navToHome = { navController.navigate(Home) },
                navBack = { navController.popBackStack() },
            )
        }

        composable<Home> {
            HomeScreen(
                homeViewModel = homeViewModel,
                navToGame = { navController.navigate(Game) },
                navToEditWord = { wordId -> navController.navigate(EditWord(wordId)) },
                navToAddWord = { navController.navigate(AddWord) },
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme
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
                wordsViewModel=wordsViewModel,
                navBack = { navController.popBackStack() },
                wordId = args.wordId
            )
        }

        composable<AddWord>{
            AddWordScreen(
                wordsViewModel=wordsViewModel,
                navBack = { navController.popBackStack() }
            )
        }
    }

}