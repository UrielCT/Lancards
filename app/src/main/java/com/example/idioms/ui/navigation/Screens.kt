package com.example.idioms.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object SignIn

@Serializable
object SignUp

@Serializable
object Home

@Serializable
object Game

@Serializable
data class EditWord(
    val wordId: Int?
)

@Serializable
object AddWord