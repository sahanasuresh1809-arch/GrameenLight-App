package com.example.grameenlight.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.grameenlight.ui.components.BottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomBar(navController, "home") },
        containerColor = Color(0xFFF5F7FA)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {
            // --- HEADER ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Grameen-Light",
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        color = Color(0xFF1B5E20)
                    )
                    Text(
                        "Village Resident Dashboard",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                
                IconButton(
                    onClick = { navController.navigate("profile") },
                    modifier = Modifier
                        .size(45.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFFE8F5E9))
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF0F9D58))
                }
            }

            Spacer(Modifier.height(30.dp))

            // --- WELCOME CARD ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF0F9D58), Color(0xFF38EF7D))
                            )
                        )
                        .padding(24.dp)
                ) {
                    Column {
                        Text(
                            "Safe Village, Bright Village",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Help your community by reporting streetlight issues and monitoring energy usage.",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(30.dp))

            Text(
                "Quick Actions",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // --- FEATURE GRID ---
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    MenuCard(
                        title = "Report Fault",
                        icon = Icons.Default.ReportProblem,
                        color = Color(0xFFD32F2F),
                        onClick = { navController.navigate("report") }
                    )
                }
                item {
                    MenuCard(
                        title = "Track Status",
                        icon = Icons.Default.History,
                        color = Color(0xFF1565C0),
                        onClick = { navController.navigate("tracker") }
                    )
                }
                item {
                    MenuCard(
                        title = "AI Insights",
                        icon = Icons.Default.AutoGraph,
                        color = Color(0xFF673AB7),
                        onClick = { navController.navigate("dashboard") }
                    )
                }
                item {
                    MenuCard(
                        title = "Energy Stats",
                        icon = Icons.Default.Bolt,
                        color = Color(0xFFF9A825),
                        onClick = { navController.navigate("energy") }
                    )
                }
            }
        }
    }
}

@Composable
fun MenuCard(title: String, icon: ImageVector, color: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(28.dp))
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}
