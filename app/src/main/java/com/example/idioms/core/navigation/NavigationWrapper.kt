package com.example.idioms.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.idioms.AddWordScreen
import com.example.idioms.GameScreen
import com.example.idioms.HomeScreen
import com.example.idioms.SignInScreen
import com.example.idioms.TutorialScreen
import com.example.idioms.addwords.ui.WordsViewModel

@Composable
fun NavigationWrapper(wordsViewModel: WordsViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SignIn){

        composable<SignIn>{
            SignInScreen(navigateToHome = {navController.navigate(Home)} )
        }

        composable<Home> {
            HomeScreen(wordsViewModel,
            navigateToTutorial = {navController.navigate(Tutorial)},
                        navigateToGame = {navController.navigate(Game)},
                        navigateToEditWord = {navController.navigate(EditWord)},
                        navigateToAddWord = {navController.navigate(AddWord)} )
        }

        composable<Tutorial>{
            TutorialScreen( {navController.popBackStack() })
        }

        composable<Game>{
            GameScreen( {navController.popBackStack() } )
        }

        composable<EditWord>{
            AddWordScreen( {navController.popBackStack() } )
        }

        composable<AddWord>{
            AddWordScreen( {navController.popBackStack() } )
        }
    }

}