package com.example.grameenlight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.grameenlight.ui.navigation.AppNavGraph
import com.example.grameenlight.ui.theme.GrameenLightTheme
import com.example.grameenlight.ui.theme.ThemeViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            val themeViewModel: ThemeViewModel = viewModel()

            GrameenLightTheme(
                darkTheme = themeViewModel.isDarkMode.value
            ) {

                AppNavGraph(
                    navController = navController,
                    themeViewModel = themeViewModel
                )
            }
        }
    }
}