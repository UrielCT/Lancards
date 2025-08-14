package com.example.idioms.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.idioms.ui.screens.addword.AddWordScreen
import com.example.idioms.ui.screens.game.GameScreen
import com.example.idioms.ui.screens.home.HomeScreen
import com.example.idioms.ui.screens.signin.SignInScreen
import com.example.idioms.ui.screens.signup.SignUpScreen
import com.example.idioms.ui.viewmodels.WordsViewModel

@Composable
fun NavigationWrapper(wordsViewModel: WordsViewModel) {

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
                wordsViewModel,
                navToGame = { navController.navigate(Game) },
                navToEditWord = { navController.navigate(EditWord) },
                navToAddWord = { navController.navigate(AddWord) }
            )
        }


        composable<Game>{
            GameScreen(
                navBack = { navController.popBackStack() }
            )
        }

        composable<EditWord>{
            AddWordScreen( {navController.popBackStack() } )
        }

        composable<AddWord>{
            AddWordScreen( {navController.popBackStack() } )
        }
    }

}