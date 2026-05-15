package com.example.grameenlight.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.grameenlight.ui.components.BottomBar
import com.example.grameenlight.viewmodel.ComplaintViewModel

@Composable
fun EnergyDashboardScreen(navController: NavController, viewModel: ComplaintViewModel = viewModel()) {

    val complaints by viewModel.complaints.collectAsState()
    
    // Calculate dynamic values from Room DB
    val fixedReports = complaints.count { it.status == "Fixed" }
    val energySaved = fixedReports * 0.24 // Assuming 0.24 kWh saved per fixed light

    Scaffold(
        bottomBar = {
            BottomBar(navController, selected = "energy")
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = "Energy Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Track village energy savings",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // MAIN ENERGY CARD
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                shape = RoundedCornerShape(28.dp),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF11998E),
                                    Color(0xFF38EF7D)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(70.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "${String.format("%.2f", energySaved)} kWh",
                            color = Color.White,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Energy Saved This Month",
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // STATS ROW
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                EnergyStatCard(
                    title = "Reports Fixed",
                    value = "$fixedReports",
                    color = Color(0xFF2E7D32)
                )

                EnergyStatCard(
                    title = "Village Rank",
                    value = "#3",
                    color = Color(0xFF1565C0)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // COMMUNITY MESSAGE
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F9FF)
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "Community Impact",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text =
                            "Your village citizens helped reduce electricity waste and improve public safety through active reporting.",
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

@Composable
fun EnergyStatCard(
    title: String,
    value: String,
    color: Color
) {

    Card(
        modifier = Modifier
            .width(160.dp)
            .height(110.dp),
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .padding(14.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = value,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}