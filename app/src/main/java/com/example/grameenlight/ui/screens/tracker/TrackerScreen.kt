package com.example.grameenlight.ui.screens.tracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.grameenlight.ui.components.BottomBar
import com.example.grameenlight.viewmodel.ComplaintViewModel

@Composable
fun TrackerScreen(navController: NavController, viewModel: ComplaintViewModel = viewModel()) {

    val complaints by viewModel.complaints.collectAsState()

    Scaffold(
        bottomBar = {
            BottomBar(navController, "tracker")
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            // 🔥 HEADER
            Text(
                text = "Complaint Tracker",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Track all your reported streetlight issues",
                color = Color.Gray
            )

            Spacer(Modifier.height(20.dp))

            // 🔥 STATUS OVERVIEW
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                TrackerStat(
                    title = "Reported",
                    count = complaints.count {
                        it.status == "Reported"
                    },
                    color = Color(0xFFD32F2F)
                )

                TrackerStat(
                    title = "Assigned",
                    count = complaints.count {
                        it.status == "Assigned"
                    },
                    color = Color(0xFFF9A825)
                )

                TrackerStat(
                    title = "Fixed",
                    count = complaints.count {
                        it.status == "Fixed"
                    },
                    color = Color(0xFF2E7D32)
                )
            }

            Spacer(Modifier.height(24.dp))

            // 🔥 COMPLAINT LIST
            LazyColumn {

                items(complaints) { complaint ->

                    val statusColor = when(complaint.status) {
                        "Reported" ->
                            Color(0xFFD32F2F)
                        "Assigned" ->
                            Color(0xFFF9A825)
                        else ->
                            Color(0xFF2E7D32)
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 14.dp),
                        shape = RoundedCornerShape(22.dp),
                        onClick = {
                            navController.navigate(
                                "complaint/${complaint.id}/${complaint.pole}/${complaint.issue}/${complaint.status}"
                            )
                        }
                    ) {

                        Column(
                            modifier = Modifier.padding(18.dp)
                        ) {

                            // 🔥 TOP ROW
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Column {

                                    Text(
                                        text = "Pole ${complaint.pole}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )

                                    Spacer(Modifier.height(4.dp))

                                    Text(
                                        text = complaint.date,
                                        color = Color.Gray,
                                        fontSize = 12.sp
                                    )
                                }

                                // STATUS CHIP
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = statusColor.copy(alpha = 0.15f)
                                ) {

                                    Text(
                                        text = complaint.status,
                                        color = statusColor,
                                        modifier = Modifier.padding(
                                            horizontal = 14.dp,
                                            vertical = 6.dp
                                        ),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(Modifier.height(18.dp))

                            // 🔥 ISSUE CARD
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFF5F5F5)
                                )
                            ) {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = when(complaint.issue) {
                                            "Fused" -> "❌"
                                            "Burning in Day" -> "🌞"
                                            else -> "💡"
                                        },
                                        fontSize = 28.sp
                                    )

                                    Spacer(Modifier.width(14.dp))

                                    Column {

                                        Text(
                                            text = complaint.issue,
                                            fontWeight = FontWeight.Bold
                                        )

                                        Spacer(Modifier.height(4.dp))

                                        Text(
                                            text = when(complaint.issue) {
                                                "Fused" ->
                                                    "Streetlight not functioning"
                                                "Burning in Day" ->
                                                    "Light ON during daytime"
                                                else ->
                                                    "Streetlight working properly"
                                            },
                                            color = Color.Gray,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }

                            Spacer(Modifier.height(18.dp))

                            // 🔥 TRACKING BAR
                            LinearProgressIndicator(
                                progress = {
                                    when(complaint.status) {
                                        "Reported" -> 0.3f
                                        "Assigned" -> 0.6f
                                        else -> 1f
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp),
                                color = statusColor
                            )

                            Spacer(Modifier.height(10.dp))

                            Text(
                                text = when(complaint.status) {
                                    "Reported" ->
                                        "Complaint submitted to Panchayat"
                                    "Assigned" ->
                                        "Maintenance team assigned"
                                    else ->
                                        "Streetlight issue resolved"
                                },
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TrackerStat(
    title: String,
    count: Int,
    color: Color
) {

    Card(
        modifier = Modifier
            .width(110.dp)
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
                text = count.toString(),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}