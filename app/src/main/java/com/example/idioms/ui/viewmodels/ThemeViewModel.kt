package com.example.idioms.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.idioms.data.datastore.ThemePreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val preferences = ThemePreferences(application)

    val isDarkTheme: StateFlow<Boolean>

    init {
        val initialValue = runBlocking { preferences.isDarkTheme.first() }
        isDarkTheme = preferences.isDarkTheme
            .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue)
    }

    fun toggleTheme() {
        viewModelScope.launch (Dispatchers.IO){
            preferences.setDarkTheme(!isDarkTheme.value)
        }
    }
}