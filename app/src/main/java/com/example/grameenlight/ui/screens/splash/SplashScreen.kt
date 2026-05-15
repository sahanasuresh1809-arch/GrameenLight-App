package com.example.grameenlight.ui.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.7f,

        animationSpec = tween(
            durationMillis = 1200
        ),

        label = ""
    )

    LaunchedEffect(true) {

        startAnimation = true

        delay(2500)

        navController.navigate("role") {

            popUpTo("splash") {
                inclusive = true
            }
        }
    }

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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scale(scale)
        ) {

            Surface(
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.2f)
            ) {

                Box(
                    modifier = Modifier
                        .size(140.dp),

                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "💡",
                        fontSize = 70.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Grameen-Light",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Citizen-led Streetlight Audit",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(50.dp))

            CircularProgressIndicator(
                color = Color.White
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Initializing Smart Village System...",
                color = Color.White
            )
        }
    }
}