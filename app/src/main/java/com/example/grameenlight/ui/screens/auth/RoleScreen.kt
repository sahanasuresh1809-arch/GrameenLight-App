package com.example.grameenlight.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.grameenlight.data.SessionManager
import com.example.grameenlight.data.UserRole

@Composable
fun RoleScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF0F9D58),
                        Color(0xFF38EF7D)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Grameen Light",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Select your role to continue",
                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(Modifier.height(40.dp))

            // 👤 RESIDENT CARD
            RoleCard(
                title = "Village Resident",
                description = "Report faulty streetlights & track status",
                color = Color.White
            ) {
                SessionManager.userRole = UserRole.RESIDENT
                navController.navigate("home") {
                    popUpTo("role") { inclusive = false }
                    launchSingleTop = true
                }
            }

            Spacer(Modifier.height(20.dp))

            // 🏛 PANCHAYAT CARD
            RoleCard(
                title = "Panchayat Admin",
                description = "Manage complaints & monitor infrastructure",
                color = Color(0xFFFFF3E0)
            ) {
                SessionManager.userRole = UserRole.ADMIN
                navController.navigate("admin") {
                    popUpTo("role") { inclusive = false }
                    launchSingleTop = true
                }
            }
        }
    }
}
@Composable
fun RoleCard(
    title: String,
    description: String,
    color: Color,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { onClick() }
            .shadow(8.dp, RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .padding(18.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = description,
                color = Color.DarkGray,
                fontSize = 14.sp
            )
        }
    }
}