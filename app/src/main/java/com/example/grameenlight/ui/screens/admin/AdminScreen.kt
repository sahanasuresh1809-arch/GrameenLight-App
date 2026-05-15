package com.example.grameenlight.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import com.example.grameenlight.data.ComplaintEntity
import com.example.grameenlight.ui.components.BottomBar
import com.example.grameenlight.viewmodel.ComplaintViewModel

@Composable
fun AdminScreen(navController: NavController, viewModel: ComplaintViewModel = viewModel()) {
    val complaints by viewModel.complaints.collectAsState()
    var selectedFilter by remember { mutableStateOf("All") }

    val filtered = complaints.filter {
        when (selectedFilter) {
            "Reported" -> it.status == "Reported"
            "Assigned" -> it.status == "Assigned"
            "Fixed" -> it.status == "Fixed"
            else -> true
        }
    }

    Scaffold(
        bottomBar = { BottomBar(navController, "admin") },
        containerColor = Color(0xFFF5F7FA)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                "Authority Dashboard",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20)
            )

            Spacer(Modifier.height(20.dp))

            // STATS ROW
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AdminStatCard("New", complaints.count { it.status == "Reported" }, Color(0xFFD32F2F), Modifier.weight(1f))
                AdminStatCard("Assigned", complaints.count { it.status == "Assigned" }, Color(0xFFF9A825), Modifier.weight(1f))
                AdminStatCard("Fixed", complaints.count { it.status == "Fixed" }, Color(0xFF2E7D32), Modifier.weight(1f))
            }

            Spacer(Modifier.height(20.dp))

            // QUICK FILTERS
            ScrollableTabRow(
                selectedTabIndex = listOf("All", "Reported", "Assigned", "Fixed").indexOf(selectedFilter),
                containerColor = Color.Transparent,
                edgePadding = 0.dp,
                divider = {}
            ) {
                listOf("All", "Reported", "Assigned", "Fixed").forEach { type ->
                    Tab(
                        selected = selectedFilter == type,
                        onClick = { selectedFilter = type },
                        text = { Text(type) }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(items = filtered, key = { it.id }) { complaint ->
                    AdminComplaintCard(
                        complaint = complaint,
                        onUpdateStatus = { newStatus -> viewModel.updateStatus(complaint.id, newStatus) },
                        onDelete = { viewModel.deleteComplaint(complaint) },
                        onClick = {
                            navController.navigate("complaint/${complaint.id}/${complaint.pole}/${complaint.issue}/${complaint.status}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AdminComplaintCard(
    complaint: ComplaintEntity,
    onUpdateStatus: (String) -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Pole ${complaint.pole}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(complaint.issue, color = Color.Gray, fontSize = 14.sp)
                }
                StatusBadge(complaint.status)
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    when (complaint.status) {
                        "Reported" -> {
                            Button(
                                onClick = { onUpdateStatus("Assigned") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Assign Worker")
                            }
                        }
                        "Assigned" -> {
                            Button(
                                onClick = { onUpdateStatus("Fixed") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Mark Fixed")
                            }
                        }
                        "Fixed" -> {
                            Text("Issue Resolved", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                        }
                    }
                }

                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.LightGray)
                }
            }
        }
    }
}

@Composable
fun AdminStatCard(title: String, count: Int, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, color = color, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Text("$count", color = color, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val color = when (status) {
        "Reported" -> Color(0xFFD32F2F)
        "Assigned" -> Color(0xFFF9A825)
        else -> Color(0xFF2E7D32)
    }
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = status,
            color = color,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
