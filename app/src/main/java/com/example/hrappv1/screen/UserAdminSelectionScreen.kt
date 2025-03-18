package com.example.hrappv1.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.hrappv1.R

@Composable
fun UserAdminSelectionScreen(
    onUserClick: () -> Unit,
    onAdminClick: () -> Unit
) {
    // Load the background image from the drawable folder
    val backgroundImage: Painter = painterResource(id = R.drawable.bg3)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image covering the entire screen
        Image(
            painter = backgroundImage,
            contentDescription = null, // Set to null for decorative images
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Scale the image to cover the entire screen
        )

        // Column to arrange buttons at the bottom
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Add padding for better spacing
            verticalArrangement = Arrangement.Bottom // Align content to the bottom
        ) {
            // Row to place buttons side by side
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp), // Add bottom padding
                horizontalArrangement = Arrangement.SpaceEvenly, // Space buttons evenly
                verticalAlignment = Alignment.CenterVertically // Align buttons vertically
            ) {
                Button(
                    onClick = onUserClick,
                    modifier = Modifier.weight(1f) // Distribute width evenly
                ) {
                    Text("Login as User")
                }

                Spacer(modifier = Modifier.width(16.dp)) // Add space between buttons

                Button(
                    onClick = onAdminClick,
                    modifier = Modifier.weight(1f) // Distribute width evenly
                ) {
                    Text("Login as Admin")
                }
            }
        }
    }
}