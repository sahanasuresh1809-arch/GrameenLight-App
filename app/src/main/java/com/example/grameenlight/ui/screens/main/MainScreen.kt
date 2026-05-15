package com.example.grameenlight.ui.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.grameenlight.ui.components.BottomBar
import com.example.grameenlight.ui.theme.ThemeViewModel

@Composable
fun MainScreen(
    navController: NavController,
    themeViewModel: ThemeViewModel
) {

    Scaffold(

        bottomBar = {
            BottomBar(navController, selected = "home")
        }

    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)   // ✅ FIXED HERE
        ) {
            // Navigation handles screens
        }
    }
}