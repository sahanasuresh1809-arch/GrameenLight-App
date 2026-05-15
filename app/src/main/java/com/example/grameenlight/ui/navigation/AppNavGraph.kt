package com.example.grameenlight.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.grameenlight.ui.screens.splash.SplashScreen
import com.example.grameenlight.ui.screens.auth.RoleScreen
import com.example.grameenlight.ui.screens.main.HomeScreen
import com.example.grameenlight.ui.screens.tracker.TrackerScreen
import com.example.grameenlight.ui.screens.dashboard.DashboardScreen
import com.example.grameenlight.ui.screens.dashboard.EnergyDashboardScreen
import com.example.grameenlight.ui.screens.settings.SettingsScreen
import com.example.grameenlight.ui.screens.admin.AdminScreen
import com.example.grameenlight.ui.screens.report.ReportScreen
import com.example.grameenlight.ui.screens.report.ReportSuccessScreen
import com.example.grameenlight.ui.theme.ThemeViewModel
import com.example.grameenlight.ui.screens.complaint.ComplaintDetailScreen
import com.example.grameenlight.ui.screens.complaint.ViewComplaintsScreen
import com.example.grameenlight.ui.screens.profile.ProfileScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    themeViewModel: ThemeViewModel
) {

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        composable("splash") {
            SplashScreen(navController)
        }

        composable("role") {
            RoleScreen(navController)
        }

        composable("home") {
            HomeScreen(navController)
        }

        composable("report") {
            ReportScreen(navController)
        }

        composable("tracker") {
            TrackerScreen(navController)
        }

        composable("view_complaints") {
            ViewComplaintsScreen(navController)
        }

        composable("dashboard") {
            DashboardScreen(navController)
        }

        composable("energy") {
            EnergyDashboardScreen(navController)
        }

        composable("profile") {
            ProfileScreen(navController)
        }

        composable("settings") {
            SettingsScreen(navController, themeViewModel)
        }

        composable("admin") {
            AdminScreen(navController)
        }

        composable("complaint/{id}/{poleId}/{issue}/{status}") { backStack ->
            val id = backStack.arguments?.getString("id") ?: ""
            val poleId = backStack.arguments?.getString("poleId") ?: ""
            val issue = backStack.arguments?.getString("issue") ?: ""
            val status = backStack.arguments?.getString("status") ?: ""

            ComplaintDetailScreen(
                navController = navController,
                complaintId = id,
                poleId = poleId,
                issue = issue,
                status = status
            )
        }

        composable("success/{poleId}/{issue}") { backStack ->
            val poleId = backStack.arguments?.getString("poleId") ?: ""
            val issue = backStack.arguments?.getString("issue") ?: ""

            ReportSuccessScreen(
                navController,
                poleId,
                issue
            )
        }
    }
}
