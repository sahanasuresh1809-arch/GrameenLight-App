package com.example.grameenlight.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Speed
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
import com.example.grameenlight.viewmodel.ComplaintViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, viewModel: ComplaintViewModel = viewModel()) {

    val complaints by viewModel.complaints.collectAsState()
    
    val totalPoles = 9 // Total poles in the village
    val fixedCount = complaints.count { it.status == "Fixed" }
    val fusedCount = complaints.count { it.status != "Fixed" && it.issue == "Fused" }
    val dayBurningCount = complaints.count { it.status != "Fixed" && it.issue == "Burning in Day" }
    val reportedCount = complaints.count { it.status != "Fixed" }
    val workingCount = (totalPoles - (fusedCount + dayBurningCount)).coerceAtLeast(0)
    
    val energySaved = fixedCount * 0.24
    val co2Reduced = energySaved * 0.33 // Example conversion factor

    Scaffold(
        containerColor = Color(0xFFF5F7FA),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0F9D58)
                ),
                title = {
                    Text(
                        text = "AI Insights Dashboard",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            item {
                Card(
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF11998E),
                                        Color(0xFF38EF7D)
                                    )
                                )
                            )
                            .padding(26.dp)
                    ) {
                        Column {
                            Text(
                                text = "Smart Village Analytics",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 26.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "AI-powered insights for energy efficient rural streetlight monitoring.",
                                color = Color.White
                            )
                        }
                    }
                }
            }

            // --- VIEW COMPLAINTS BUTTON ---
            item {
                Button(
                    onClick = { navController.navigate("view_complaints") },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F9D58))
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("View My Complaints", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    }
                }
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                tint = Color(0xFFF9A825),
                                modifier = Modifier.size(42.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "${String.format(Locale.getDefault(), "%.1f", energySaved)} kWh",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = "Energy Saved")
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Eco,
                                contentDescription = null,
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(42.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "${String.format(Locale.getDefault(), "%.1f", co2Reduced)} kg",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = "CO₂ Reduced")
                        }
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(22.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = Color(0xFF0F9D58)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Streetlight Statistics",
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        Text(text = "• Total Streetlights : $totalPoles", fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "• Working Properly : $workingCount", fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "• Fused Lights : $fusedCount", fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "• Day Burning Cases : $dayBurningCount", fontSize = 18.sp)
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1E1E1E)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Speed,
                                contentDescription = null,
                                tint = Color(0xFF38EF7D)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "AI Insights",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))
                        if (dayBurningCount > 0) {
                            Text(text = "• Detected $dayBurningCount poles wasting energy in daylight.", color = Color.White)
                        } else {
                            Text(text = "• No daytime energy wastage detected today.", color = Color.White)
                        }
                        
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "• Village energy efficiency improved by ${if (fixedCount > 0) "12%" else "0%"}.",
                            color = Color.White
                        )
                        
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "• Repair response time is ${if (reportedCount > 2) "slower" else "optimal"} this week.",
                            color = Color.White
                        )
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(22.dp)
                    ) {
                        Text(
                            text = "Monthly Performance",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        val efficiency = if (totalPoles > 0) (workingCount.toFloat() / totalPoles).coerceIn(0f, 1f) else 1f
                        LinearProgressIndicator(
                            progress = { efficiency },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "${(efficiency * 100).toInt()}% energy efficiency achieved this month."
                        )
                    }
                }
            }
        }
    }
}
