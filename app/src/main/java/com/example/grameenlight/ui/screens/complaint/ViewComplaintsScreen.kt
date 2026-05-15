package com.example.grameenlight.ui.screens.complaint

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.grameenlight.data.ComplaintEntity
import com.example.grameenlight.viewmodel.ComplaintViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewComplaintsScreen(
    navController: NavController,
    viewModel: ComplaintViewModel = viewModel()
) {
    val complaints by viewModel.complaints.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Complaints", fontWeight = FontWeight.Bold) },
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
        containerColor = Color(0xFFF5F7FA)
    ) { paddingValues ->
        if (complaints.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.History,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("No complaints found", color = Color.Gray, fontSize = 18.sp)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(complaints) { complaint ->
                    ComplaintCard(complaint)
                }
            }
        }
    }
}

@Composable
fun ComplaintCard(complaint: ComplaintEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ID: ${complaint.id.takeLast(6)}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                StatusBadge(complaint.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Pole Number: ${complaint.pole}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1B5E20)
            )

            Text(
                text = "Issue: ${complaint.issue}",
                fontSize = 16.sp,
                color = Color.Black
            )

            if (complaint.note.isNotEmpty()) {
                Text(
                    text = "Note: ${complaint.note}",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Reported on: ${complaint.date}",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val backgroundColor = when (status) {
        "Reported" -> Color(0xFFE3F2FD)
        "In Progress" -> Color(0xFFFFF3E0)
        "Fixed" -> Color(0xFFE8F5E9)
        else -> Color(0xFFF5F5F5)
    }
    val contentColor = when (status) {
        "Reported" -> Color(0xFF1976D2)
        "In Progress" -> Color(0xFFF57C00)
        "Fixed" -> Color(0xFF388E3C)
        else -> Color(0xFF616161)
    }

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = contentColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
