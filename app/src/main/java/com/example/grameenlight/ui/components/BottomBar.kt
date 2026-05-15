package com.example.grameenlight.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.Bolt
import com.example.grameenlight.data.SessionManager
import com.example.grameenlight.data.UserRole

@Composable
fun BottomBar(navController: NavController, selected: String) {
    val role = SessionManager.userRole
    NavigationBar {

        NavigationBarItem(
            selected = selected == "home",
            onClick = { navController.navigate("home") },
            icon = { Icon(Icons.Filled.Home, null) },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = selected == "tracker",
            onClick = { navController.navigate("tracker") },
            icon = { Icon(Icons.Filled.List, null) },
            label = { Text("Track") }
        )

        NavigationBarItem(
            selected = selected == "energy",
            onClick = { navController.navigate("energy") },
            icon = { Icon(Icons.Filled.Bolt, null) },
            label = { Text("Energy") }
        )

        if (role == UserRole.ADMIN) {
            NavigationBarItem(
                selected = selected == "admin",
                onClick = { navController.navigate("admin") },
                icon = { Icon(Icons.Filled.Person, null) },
                label = { Text("Admin") }
            )
        }

        NavigationBarItem(
            selected = selected == "settings",
            onClick = { navController.navigate("settings") },
            icon = { Icon(Icons.Filled.Settings, null) },
            label = { Text("Settings") }
        )
    }
}