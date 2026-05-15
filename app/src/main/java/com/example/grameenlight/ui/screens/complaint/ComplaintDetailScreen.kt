package com.example.grameenlight.ui.screens.complaint

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun ComplaintDetailScreen(
    navController: NavController,
    complaintId: String,
    poleId: String,
    issue: String,
    status: String,
    viewModel: ComplaintViewModel = viewModel()
) {
    var selectedStatus by remember { mutableStateOf(status) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFE8F5E9), Color.White)
                )
            )
            .padding(18.dp)
    ) {
        Text(
            text = "Complaint Details",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Pole ID", color = Color.Gray)
                        Spacer(Modifier.height(4.dp))
                        Text(text = poleId, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                    }

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = when (selectedStatus) {
                            "Reported" -> Color(0xFFD32F2F).copy(alpha = 0.15f)
                            "Assigned" -> Color(0xFFF9A825).copy(alpha = 0.15f)
                            else -> Color(0xFF2E7D32).copy(alpha = 0.15f)
                        }
                    ) {
                        Text(
                            text = selectedStatus,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            color = when (selectedStatus) {
                                "Reported" -> Color(0xFFD32F2F)
                                "Assigned" -> Color(0xFFF9A825)
                                else -> Color(0xFF2E7D32)
                            },
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                LinearProgressIndicator(
                    progress = {
                        when (selectedStatus) {
                            "Reported" -> 0.3f
                            "Assigned" -> 0.6f
                            else -> 1f
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = when (selectedStatus) {
                        "Reported" -> Color(0xFFD32F2F)
                        "Assigned" -> Color(0xFFF9A825)
                        else -> Color(0xFF2E7D32)
                    }
                )

                Spacer(Modifier.height(18.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(text = "Issue Type", color = Color.Gray)
                        Spacer(Modifier.height(6.dp))
                        Text(text = issue, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                }

                Spacer(Modifier.height(20.dp))

                Text(text = "Update Status", fontWeight = FontWeight.Bold)

                Spacer(Modifier.height(14.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    StatusChip(
                        title = "Reported",
                        selected = selectedStatus == "Reported"
                    ) { selectedStatus = "Reported" }

                    StatusChip(
                        title = "Assigned",
                        selected = selectedStatus == "Assigned"
                    ) { selectedStatus = "Assigned" }

                    StatusChip(
                        title = "Fixed",
                        selected = selectedStatus == "Fixed"
                    ) { selectedStatus = "Fixed" }
                }

                Spacer(Modifier.height(28.dp))

                Button(
                    onClick = {
                        viewModel.updateStatus(complaintId, selectedStatus)
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F9D58))
                ) {
                    Text(text = "Save & Go Back", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun StatusChip(title: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.padding(4.dp),
        shape = RoundedCornerShape(50),
        color = if (selected) Color(0xFF0F9D58) else Color(0xFFE0E0E0),
        onClick = onClick
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
            color = if (selected) Color.White else Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}