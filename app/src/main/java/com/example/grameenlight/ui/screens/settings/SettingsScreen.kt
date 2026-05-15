package com.example.grameenlight.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.grameenlight.ui.theme.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    themeViewModel: ThemeViewModel
) {

    val isDarkMode = themeViewModel.isDarkMode.value


    val backgroundColor =
        if (isDarkMode) Color(0xFF121212)
        else Color(0xFFF5F7FA)

    val textColor =
        if (isDarkMode) Color.White
        else Color.Black

    Scaffold(

        containerColor = backgroundColor,

        topBar = {

            TopAppBar(

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0F9D58)
                ),

                title = {

                    Text(
                        text = "Settings",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(18.dp)
        ) {

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {

                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF141E30),
                                    Color(0xFF243B55)
                                )
                            )
                        )
                        .padding(24.dp)
                ) {

                    Column {

                        Text(
                            text = "🌙 Night Audit Mode",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Enable dark inspection mode for nighttime streetlight audits.",
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.DarkMode,
                                contentDescription = null,
                                tint = Color.White
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text =
                                    if (isDarkMode)
                                        "Night Mode Enabled"
                                    else
                                        "Night Mode Disabled",

                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Switch(

                                checked = isDarkMode,

                                onCheckedChange = {

                                    themeViewModel.toggleTheme()
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                shape = RoundedCornerShape(20.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            tint = Color(0xFF0F9D58)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "Notifications",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = textColor
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Get updates when streetlights are repaired.",
                        color = textColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Card(
                shape = RoundedCornerShape(20.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color(0xFF0F9D58)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "About Grameen-Light",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = textColor
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Smart village streetlight monitoring system for energy saving and citizen reporting.",
                        color = textColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            if (isDarkMode) {

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1E1E1E)
                    ),

                    shape = RoundedCornerShape(24.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(22.dp)
                    ) {

                        Text(
                            text = "🌃 Night Audit Active",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Village inspectors can now identify fused or day-burning poles more clearly during nighttime audits.",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}