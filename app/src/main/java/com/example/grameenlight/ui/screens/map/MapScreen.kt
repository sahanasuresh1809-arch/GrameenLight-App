package com.example.grameenlight.ui.screens.map

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lightbulb
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavController, viewModel: ComplaintViewModel = viewModel()) {
    val complaints by viewModel.complaints.collectAsState()
    
    // Village simulation: 12 poles
    val poleIds = remember { (1..12).map { "P$it" } }

    var selectedPoleId by remember { mutableStateOf<String?>(null) }
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Handle physical back button
    BackHandler(enabled = showSheet) {
        showSheet = false
        selectedPoleId = null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Village Pole Map", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0F9D58),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        bottomBar = { BottomBar(navController, "home") },
        containerColor = Color(0xFFF5F7FA)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Legend Card
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    LegendItem("Working", Color(0xFF2E7D32))
                    LegendItem("Fused", Color(0xFFD32F2F))
                    LegendItem("Burning", Color(0xFFF9A825))
                    LegendItem("Assigned", Color(0xFF1565C0))
                }
            }

            Text(
                "Village Grid View",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(poleIds) { poleId ->
                    val active = complaints.findActiveForPole(poleId)
                    val color = getPoleStatusColor(active)

                    PoleCard(
                        poleId = poleId,
                        status = active?.status ?: "Working",
                        color = color,
                        onClick = {
                            selectedPoleId = poleId
                            showSheet = true
                        }
                    )
                }
            }
        }
    }

    if (showSheet && selectedPoleId != null) {
        val currentPoleId = selectedPoleId!!
        val active = complaints.findActiveForPole(currentPoleId)

        ModalBottomSheet(
            onDismissRequest = { 
                showSheet = false
                selectedPoleId = null
            },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 48.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Pole $currentPoleId Actions", 
                    style = MaterialTheme.typography.headlineSmall, 
                    fontWeight = FontWeight.Bold
                )
                
                if (active != null) {
                    Spacer(Modifier.height(16.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(Modifier.padding(16.dp).fillMaxWidth()) {
                            Text("Current Active Issue", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                            Text("Issue: ${active.issue}", fontSize = 14.sp)
                            Text("Status: ${active.status}", fontWeight = FontWeight.Bold, color = getPoleStatusColor(active))
                            Spacer(Modifier.height(12.dp))
                            Button(
                                onClick = {
                                    showSheet = false
                                    selectedPoleId = null
                                    navController.navigate("complaint/${active.id}/${active.pole}/${active.issue}/${active.status}")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F9D58))
                            ) {
                                Text("View Full Details")
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))
                Text("Report New Status:", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 14.sp)
                Spacer(Modifier.height(12.dp))
                
                StatusOptionCard("Working / Fixed", "Light is functioning perfectly", "💡", Color(0xFF2E7D32)) {
                    showSheet = false
                    selectedPoleId = null
                    navController.navigate("report/$currentPoleId/Working")
                }
                StatusOptionCard("Fused", "Light is OFF or damaged", "❌", Color(0xFFD32F2F)) {
                    showSheet = false
                    selectedPoleId = null
                    navController.navigate("report/$currentPoleId/Fused")
                }
                StatusOptionCard("Burning in Day", "ON during daylight hours", "🌞", Color(0xFFF9A825)) {
                    showSheet = false
                    selectedPoleId = null
                    navController.navigate("report/$currentPoleId/Burning")
                }
            }
        }
    }
}

private fun List<ComplaintEntity>.findActiveForPole(poleId: String): ComplaintEntity? {
    return this.filter { it.pole == poleId }
        .sortedByDescending { it.date }
        .firstOrNull { it.status != "Fixed" }
}

private fun getPoleStatusColor(active: ComplaintEntity?): Color {
    return when {
        active == null -> Color(0xFF2E7D32) // Working (Green)
        active.status == "Assigned" -> Color(0xFF1565C0) // Assigned (Blue)
        active.issue.contains("Fused", ignoreCase = true) -> Color(0xFFD32F2F) // Fused (Red)
        active.issue.contains("Burning", ignoreCase = true) -> Color(0xFFF9A825) // Burning (Yellow)
        else -> Color(0xFFD32F2F) // Default fault color
    }
}

@Composable
fun StatusOptionCard(title: String, desc: String, icon: String, color: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.05f)),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.2f))
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier.size(40.dp),
                color = color.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(icon, fontSize = 20.sp)
                }
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(title, fontWeight = FontWeight.Bold, color = color)
                Text(desc, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun LegendItem(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(10.dp).background(color, RoundedCornerShape(2.dp)))
        Spacer(Modifier.width(6.dp))
        Text(label, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
    }
}

@Composable
fun PoleCard(poleId: String, status: String, color: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.Lightbulb, null, tint = color, modifier = Modifier.size(24.dp))
            Spacer(Modifier.height(8.dp))
            Text(poleId, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(
                text = if (status == "Reported") "Faulty" else status, 
                fontSize = 10.sp, 
                color = color, 
                fontWeight = FontWeight.Bold, 
                maxLines = 1
            )
        }
    }
}
