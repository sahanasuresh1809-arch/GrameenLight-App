package com.example.grameenlight.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ThemeViewModel : ViewModel() {

    var isDarkMode = mutableStateOf(false)

    fun toggleTheme() {
        isDarkMode.value = !isDarkMode.value
    }
}