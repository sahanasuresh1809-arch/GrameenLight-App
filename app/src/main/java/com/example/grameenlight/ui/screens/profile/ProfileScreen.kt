package com.example.grameenlight.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.grameenlight.ui.components.BottomBar

@Composable
fun ProfileScreen(
    navController: NavController
) {

    Scaffold(

        bottomBar = {
            BottomBar(navController, "profile")
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFFE8F5E9),
                            Color.White
                        )
                    )
                )
                .padding(padding)
                .padding(18.dp)
        ) {

            // 👤 PROFILE HEADER
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF0F9D58)
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "👤",
                        fontSize = 64.sp
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "Village Citizen",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = "Grameen-Light User",
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // 📊 STATS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                ProfileStat(
                    title = "Reports",
                    value = "12",
                    color = Color(0xFF1565C0)
                )

                ProfileStat(
                    title = "Resolved",
                    value = "8",
                    color = Color(0xFF2E7D32)
                )

                ProfileStat(
                    title = "Pending",
                    value = "4",
                    color = Color(0xFFD32F2F)
                )
            }

            Spacer(Modifier.height(28.dp))

            // ⚙ SETTINGS CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp)
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    ProfileOption(
                        icon = "⚙",
                        title = "Settings"
                    ) {
                        navController.navigate("settings")
                    }

                    Divider()

                    ProfileOption(
                        icon = "🌙",
                        title = "Dark Mode"
                    ) {
                        navController.navigate("settings")
                    }

                    Divider()

                    ProfileOption(
                        icon = "📍",
                        title = "Village Information"
                    ) {

                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            // 🚪 LOGOUT BUTTON
            OutlinedButton(

                onClick = {
                    navController.navigate("role")
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                shape = RoundedCornerShape(18.dp)
            ) {

                Text(
                    text = "Logout",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun ProfileStat(
    title: String,
    value: String,
    color: Color
) {

    Card(
        modifier = Modifier
            .width(105.dp)
            .height(90.dp),

        shape = RoundedCornerShape(18.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .padding(12.dp),

            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = value,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ProfileOption(
    icon: String,
    title: String,
    onClick: () -> Unit
) {

    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = icon,
                fontSize = 22.sp
            )

            Spacer(Modifier.width(14.dp))

            Text(
                text = title,
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }
}