package com.example.grameenlight.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ComplaintDetailScreen(
    navController: NavController,
    complaintId: String = "GL-001"
) {

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                "Complaint Details",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            // CARD 1 — BASIC INFO
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {

                Column(Modifier.padding(16.dp)) {

                    Text("Complaint ID: $complaintId", fontWeight = FontWeight.Bold)
                    Text("Pole ID: VPL003")
                    Text("Status: Reported")
                    Text("Infrastructure Point: Village Main Line")
                }
            }

            Spacer(Modifier.height(12.dp))

            // CARD 2 — TIMELINE
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {

                Column(Modifier.padding(16.dp)) {

                    Text("Reported At", fontWeight = FontWeight.Bold)
                    Text("12 Jan 2026 - 10:45 AM")

                    Spacer(Modifier.height(8.dp))

                    Text("Issue Type: Fused Lamp")
                    Text("Internal Note: Near School Gate")
                }
            }

            Spacer(Modifier.height(12.dp))

            // IMAGE PLACEHOLDER
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(16.dp)
            ) {

                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text("Site Image Placeholder")
                }
            }

            Spacer(Modifier.height(16.dp))

            Text("Update Status", fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                Button(onClick = { }) {
                    Text("Mark Assigned")
                }

                Button(onClick = { }) {
                    Text("Mark Fixed")
                }
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update & Go Back")
            }
        }
    }
}