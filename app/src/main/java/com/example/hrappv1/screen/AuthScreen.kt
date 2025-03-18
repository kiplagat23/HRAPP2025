package com.example.hrappv1.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hrappv1.R

@Composable
fun AuthScreen(
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.bg4), // Replace with your background image resource
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Ensures the image covers the entire screen
        )

        // Content Column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.user2),
                contentDescription = "App Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Welcome to Human Resource App",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = Color.White // Adjust text color for better visibility
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Get HR services here",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = Color.White // Adjust text color for better visibility
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    text = "Login",
                    fontSize = 22.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onSignUpClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    text = "Signup",
                    fontSize = 22.sp
                )
            }
        }
    }
}